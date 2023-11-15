import Loading from "components/atoms/Loading";
import { OrderHistoryContainer } from "./style";
import { useState, useEffect } from "react";
import { getCompleteOrders, getProcessingOrders } from "api/sale";
import { AxiosResponse } from "axios";
import OrderHistoryItem from "components/atoms/OrderHistoryItem";
import NothingHere from "components/atoms/NothingHere";

interface IOrderHistoryListProps {
  selectedOrderHistory: number;
}

interface IOrderItems {
  orderId: number;
  status: string;
  orderCnt: number;
  totalPrice: number;
  createdTime: string;
  expectTime: number;
  menuInfos: IMenuInfo[];
}

interface IMenuInfo {
  menuId: number;
  quantity: number;
  menuName: string;
  price: number;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function OrderHistoryList({ selectedOrderHistory }: IOrderHistoryListProps) {
  const [loading, setLoading] = useState<boolean>(true);
  const [processingOrderList, setProcessingOrderList] = useState<IOrderItems[]>([]);
  const [completeOrderList, setCompleteOrderList] = useState<IOrderItems[]>([]);

  const selectedFoodTruckId: number = Number.parseInt(
    localStorage.getItem("selectedFoodTruckId") || "0"
  );

  const getProcessingData = () => {
    getProcessingOrders(
      selectedFoodTruckId,
      (response: AxiosResponse) => {
        const data = getData(response);
        setProcessingOrderList(data["orderItems"]);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const getCompleteData = () => {
    getCompleteOrders(
      selectedFoodTruckId,
      (response: AxiosResponse) => {
        const data = getData(response);
        setCompleteOrderList(data["orderItems"]);
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const fetchData = () => {
    if (selectedFoodTruckId === undefined || selectedFoodTruckId === 0) {
      return;
    }
    getProcessingData();
    getCompleteData();
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <OrderHistoryContainer>
      {loading ? (
        <Loading />
      ) : selectedOrderHistory === 1 ? (
        processingOrderList === undefined || processingOrderList.length === 0 ? (
          <NothingHere />
        ) : (
          processingOrderList.map((data: IOrderItems, index: number) => (
            <OrderHistoryItem
              key={index}
              orderId={data.orderId}
              createdTime={data.createdTime}
              orderCnt={data.orderCnt}
              totalPrice={data.totalPrice}
              expectTime={data.expectTime}
              menuInfos={data.menuInfos}
            />
          ))
        )
      ) : selectedOrderHistory === 2 ? (
        completeOrderList === undefined || completeOrderList.length === 0 ? (
          <NothingHere />
        ) : (
          completeOrderList.map((data: IOrderItems, index: number) => (
            <OrderHistoryItem
              key={index}
              orderId={data.orderId}
              createdTime={data.createdTime}
              orderCnt={data.orderCnt}
              totalPrice={data.totalPrice}
              expectTime={data.expectTime}
              menuInfos={data.menuInfos}
            />
          ))
        )
      ) : (
        <NothingHere />
      )}
    </OrderHistoryContainer>
  );
}

export default OrderHistoryList;
