import BackSpace from "components/atoms/BackSpace";
import { CartOrderPageLayout } from "./style";
import Navbar from "components/organisms/Navbar";
import axios, { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import { getCartOrder } from "api/cart";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router-dom";
import { createOrder } from "api/order";

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function CartOrderPage() {
  const navigate = useNavigate();
  const [cartOrder, setCartOrder] = useState<null | {
    foodTruckName: "";
    prepareTime: number;
    phoneNumber: "";
    totalPrice: number;
  }>(null);

  useEffect(() => {
    const fetchData = async () => {
      getCartOrder(
        (response: AxiosResponse) => {
          const data = getData(response);
          console.log(data);
          setCartOrder(data);
        },
        (error: any) => {
          console.log(error);
          setCartOrder(null);
        }
      );
    };
    fetchData();
    const jquery = document.createElement("script");
    jquery.src = "http://code.jquery.com/jquery-1.12.4.min.js";
    const iamport = document.createElement("script");
    iamport.src = "http://cdn.iamport.kr/js/iamport.payment-1.1.7.js";
    document.head.appendChild(jquery);
    document.head.appendChild(iamport);


  // 스크립트 로드 확인
  const checkScriptLoad = () => {
    if ((window as any).IMP) {
      const IMP = (window as any).IMP;
      IMP.init(process.env.REACT_APP_PAYMENT_CODE);
    } else {
      // 아임포트 스크립트 로드 실패 시 처리
      console.error("아임포트 스크립트 로드 실패");
    }
  };

  // 스크립트 로드 완료 확인
  iamport.onload = checkScriptLoad;


    return () => {
      document.head.removeChild(jquery);
      document.head.removeChild(iamport);
    };

  }, []);

  const handlePayment = () => {
    navigate("/"); // 결제 페이지로 이동
  };

  const requestPay = () => {
    const IMP = (window as any).IMP;
    IMP.init(`${process.env.REACT_APP_PAYMENT_CODE}`);

    IMP.request_pay(
      {
        pg: "kakaopay.TC0ONETIME",
        pay_method: "card",
        merchant_uid: new Date().getTime(),
        name: "당근트럭_포장주문",
        amount: cartOrder?.totalPrice
      },
      async (rsp: { imp_uid: string; paid_amount: any }) => {
        try {
          const { data } = await axios.post(
            "http://localhost:8001/api/verifyIamport/" + rsp.imp_uid
          );
          if (rsp.paid_amount === data.data.response.amount) {
            alert("결제 성공");
            createOrder(
              (response: AxiosResponse) => {
                const data = getData(response);
                console.log("결제 완료 데이터: ",data);
                navigate("/cart");
                // 주문내역으로 이동하기
              },
              (error: any) => {
                console.log("결제시도 오류: ",error);
                navigate("/cart");
              }
            )
            navigate("/");
          } else {
            alert("결제 실패");
            navigate("/cart");
          }
        } catch (error) {
          console.error("Error while verifying payment:", error);
          alert("결제 실패");
        }
      }
    );
  };

  return (
    <CartOrderPageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>주문하기</p>
      </div>
      <div>
        <p>{cartOrder?.foodTruckName}</p>
        <p>{cartOrder?.prepareTime}</p>
        <p>{cartOrder?.phoneNumber}</p>
        <p>{cartOrder?.totalPrice}</p>
      </div>
      <Button
        handleClick={requestPay}
        color="Primary"
        size="full"
        radius="m"
        text= "결제하기"
      />
      <Navbar />
    </CartOrderPageLayout>
  );
}

export default CartOrderPage;
