import { OrderHistoryItemWrapper } from "./style";
import { useState } from "react";
import down from "../../../assets/icons/down_arrow.svg";
import up from "../../../assets/icons/up_arrow.svg";
import Button from "../Button";

interface IOrderHistoryItemProps {
  orderId: number;
  createdTime: string;
  orderCnt: number;
  totalPrice: number;
  expectTime: number;
  menuInfos: IMenuInfo[];
}

interface IMenuInfo {
  menuId: number;
  quantity: number;
  menuName: string;
  price: number;
}

const getMenuSummary = (menus: IMenuInfo[]) => {
  const menuArray = [];
  for (const menu of menus) {
    menuArray.push(`${menu.menuName} x${menu.quantity}`);
  }
  return menuArray.join(", ");
};

function OrderHistoryItem({
  orderId,
  createdTime,
  orderCnt,
  totalPrice,
  expectTime,
  menuInfos,
}: IOrderHistoryItemProps) {
  const [showDetail, setShowDetail] = useState<boolean>(false);

  const handleArrowClick = () => {
    setShowDetail(!showDetail);
  };

  return (
    <OrderHistoryItemWrapper>
      {!showDetail ? (
        <>
          <div>{createdTime}</div>
          <div>{getMenuSummary(menuInfos)}</div>
          <div>{totalPrice}</div>
          <div className="arrow-wrapper" onClick={handleArrowClick}>
            <img src={down} alt="" />
          </div>
        </>
      ) : (
        <>
          <div>{createdTime}</div>
          <div>{getMenuSummary(menuInfos)}</div>
          <div>{totalPrice}</div>
          <div className="arrow-wrapper" onClick={handleArrowClick}>
            <img src={up} alt="" />
          </div>
          <div>{orderCnt}</div>
          <div>{expectTime}</div>
          <Button
            size={"s"}
            radius={"s"}
            color={"Primary"}
            text={"text"}
            handleClick={() => {
              console.log(orderId);
            }}
          />
        </>
      )}
    </OrderHistoryItemWrapper>
  );
}

export default OrderHistoryItem;
