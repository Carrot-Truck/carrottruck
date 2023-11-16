import Loading from "components/atoms/Loading";
import { OrderHistoryContainer } from "./style";
import { useState, useEffect } from "react";
import { getCompleteOrders, getProcessingOrders } from "api/sale";
import { AxiosResponse } from "axios";
import OrderHistoryItem from "components/atoms/OrderHistoryItem";
import NothingHere from "components/atoms/NothingHere";

interface IOrderHistoryListProps {
  selectedOrderHistory: number;
  vendorEmail: string;
}

interface IOrderItems {
  orderId: number;
  status: string;
  orderCnt: number;
  totalPrice: number;
  createdTime: string;
  expectTime: string;
  orderMenuItems: IMenuInfo[];
  clientInfo: IClientInfo;
}

interface IMenuInfo {
  menuId: number;
  quantity: number;
  menuName: string;
  price: number;
}

interface IClientInfo {
  nickname: string;
  phoneNumber: string;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function OrderHistoryList({ selectedOrderHistory, vendorEmail }: IOrderHistoryListProps) {
  const [loading, setLoading] = useState<boolean>(true);
  const [processingOrderList, setProcessingOrderList] = useState<IOrderItems[]>([]);
  const [completeOrderList, setCompleteOrderList] = useState<IOrderItems[]>([]);
  const [openOrderId, setOpenOrderId] = useState<number>(0);

  const selectedFoodTruckId: number = Number.parseInt(
    localStorage.getItem("selectedFoodTruckId") || "0"
  );

  useEffect(() => {
    const eventSource = new EventSource(
      `${process.env.REACT_APP_API_URL}/order/subscribe/${vendorEmail}`
    );

    eventSource.addEventListener("sse", (event) => {
      if (event.data === "connect completed") {
        console.log("SSE 연결 성공함");
        return;
      } else if (event.data === "1") {
        fetchData();
      }
    });

    return () => {
      console.log("sse 연결을 닫습니다");
      eventSource.close();
    };
  }, []);

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

  const fetchOrderLists = (orderId: number) => {
    setOpenOrderId(orderId);
    setLoading(true);
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
              status={data.status}
              expectTime={data.expectTime}
              menuInfos={data.orderMenuItems}
              clientInfo={data.clientInfo}
              fetchOrderLists={fetchOrderLists}
              openOrder={openOrderId === data.orderId}
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
              status={data.status}
              expectTime={data.expectTime}
              menuInfos={data.orderMenuItems}
              clientInfo={data.clientInfo}
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
