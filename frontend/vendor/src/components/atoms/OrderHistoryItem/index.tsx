import { OrderHistoryItemWrapper } from "./style";
import { useEffect, useRef, useState } from "react";
import down from "../../../assets/icons/down_arrow.svg";
import up from "../../../assets/icons/up_arrow.svg";
import Button from "../Button";
import { accept, complete, decline } from "api/sale";
import { AxiosResponse } from "axios";
import Input from "../Input";

interface IOrderHistoryItemProps {
  orderId: number;
  createdTime: string;
  orderCnt: number;
  totalPrice: number;
  status: string;
  expectTime: string;
  menuInfos: IMenuInfo[];
  clientInfo: IClientInfo;
  fetchOrderLists?: (orderId: number) => void;
  openOrder?: boolean;
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

interface IMenuString {
  menuName: string;
  price: string;
}

const statusText: Record<string, string> = {
  PENDING: "대기중",
  PROCESSING: "준비중",
  COMPLETE: "주문 완료",
  DECLINED: "거절",
  CANCELLED: "취소",
};

const getMenuSummary = (menus: IMenuInfo[]) => {
  if (menus === undefined || menus.length === 0) {
    return;
  }

  const menuArray = [];
  for (const menu of menus) {
    menuArray.push(`${menu.menuName} x${menu.quantity}`);
  }

  return menuArray.join(", ");
};

const getOrderCountString = (orderCnt: number) => {
  if (orderCnt === 1) {
    return `처음 주문한 고객이에요`;
  } else {
    return `${orderCnt}번째 주문한 고객이에요`;
  }
};

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function OrderHistoryItem({
  orderId,
  createdTime,
  orderCnt,
  totalPrice,
  status,
  expectTime,
  menuInfos,
  clientInfo,
  fetchOrderLists,
  openOrder,
}: IOrderHistoryItemProps) {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const [menuStrings, setMenuStrings] = useState<IMenuString[]>([]);
  const [pendingExpect, setPendingExpect] = useState<number | null>(null);

  const expectTimeRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setMenuStrings([]);
    setMenuStrings(() => {
      const newMenuStrings = menuInfos.map((menuInfo) => ({
        menuName: `${menuInfo.menuName} x${menuInfo.quantity}`,
        price: `${menuInfo.price}`,
      }));
      return newMenuStrings;
    });
    if (openOrder) {
      setShowDetail(true);
    }
  }, []);

  const handleArrowClick = () => {
    setShowDetail(!showDetail);
  };

  const acceptClick = () => {
    if (!pendingExpect) {
      alert("예상 대기시간을 입력해주세요.");
      expectTimeRef.current?.focus();
      return;
    }

    const requestBody = {
      orderId: orderId,
      prepareTime: pendingExpect,
    };

    accept(
      requestBody,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data == orderId && fetchOrderLists !== undefined) {
          fetchOrderLists(orderId);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const declineClick = () => {
    const requestBody = {
      orderId: orderId,
      reason: "",
    };
    decline(
      requestBody,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data == orderId && fetchOrderLists !== undefined) {
          fetchOrderLists(orderId);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const readyClick = () => {
    const requestBody = {
      orderId: orderId,
    };
    complete(
      requestBody,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data == orderId && fetchOrderLists !== undefined) {
          fetchOrderLists(orderId);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  return (
    <OrderHistoryItemWrapper>
      <div className="order-history-item">
        <div className="left-column">
          <div>{createdTime}</div>
          <div>{getMenuSummary(menuInfos)}</div>
          <div>{totalPrice.toLocaleString()} 원</div>
        </div>
        <div className="right-column">
          <div className="status">{statusText[status]}</div>
          <div className="arrow-wrapper" onClick={handleArrowClick}>
            <img src={showDetail ? up : down} alt="" />
          </div>
        </div>
      </div>
      {showDetail && (
        <>
          <div className="order-history-detail">
            <div className="client-info">
              <div className="middle-title">주문자 정보</div>
              <div className="client-info-detail">
                <div className="text-box-left">
                  <div>닉네임</div>
                  <div>연락처</div>
                </div>
                <div className="text-box-right">
                  <div>{clientInfo.nickname}</div>
                  <div>{clientInfo.phoneNumber}</div>
                </div>
              </div>
              <div className="order-count">{getOrderCountString(orderCnt)}</div>
            </div>
            <div className="order-info">
              <div className="middle-title">주문 항목</div>
              {menuStrings.map((data: IMenuString, index: number) => (
                <div className="menu-info-items" key={index}>
                  <div>{data.menuName}</div>
                  <div>{data.price.toLocaleString()}원</div>
                </div>
              ))}
              <div className="total-price">
                <div>총 결제 금액</div>
                <div>{totalPrice.toLocaleString()}원</div>
              </div>
            </div>
          </div>
          {status === "PENDING" ? (
            <>
              <Input
                type={"number"}
                placeholder={"예상 대기 시간을 입력해주세요.(분)"}
                value={pendingExpect}
                setValue={setPendingExpect}
              />
              <div className="button-wrapper">
                <Button
                  size={"s"}
                  radius={"s"}
                  color={"Primary"}
                  text={"수락"}
                  handleClick={acceptClick}
                />
                <Button
                  size={"s"}
                  radius={"s"}
                  color={"Normal"}
                  text={"거절"}
                  handleClick={declineClick}
                />
              </div>
            </>
          ) : status === "PROCESSING" ? (
            <>
              <div className="expect-time">예상 완료 시간: {expectTime}</div>
              <div className="button-wrapper">
                <Button
                  size={"m"}
                  radius={"m"}
                  color={"Primary"}
                  text={"준비완료"}
                  handleClick={readyClick}
                />
              </div>
            </>
          ) : (
            <></>
          )}
        </>
      )}
    </OrderHistoryItemWrapper>
  );
}

export default OrderHistoryItem;
