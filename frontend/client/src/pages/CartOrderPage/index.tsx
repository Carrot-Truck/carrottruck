import BackSpace from "components/atoms/BackSpace";
import { CartOrderPageLayout } from "./style";
import Navbar from "components/organisms/Navbar";
import axios, { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import { getCartOrder } from "api/cart";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router-dom";

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
    IMP.init("가맹점식별코드");

    IMP.request_pay(
      {
        pg: "kakaopay",
        pay_method: "card",
        merchant_uid: new Date().getTime(),
        name: "테스트 상품",
        amount: 1004,
        buyer_email: "test@naver.com",
        buyer_name: "코드쿡",
        buyer_tel: "010-1234-5678",
        buyer_addr: "서울특별시",
        buyer_postcode: "123-456",
      },
      async (rsp: { imp_uid: string; paid_amount: any }) => {
        try {
          const { data } = await axios.post(
            "http://localhost:8001/api/verifyIamport/imp18617674"
          );
          if (rsp.paid_amount === data.response.amount) {
            alert("결제 성공");
          } else {
            alert("결제 실패");
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
      <div>
        <button onClick={requestPay}>결제하기</button>
      </div>
      {/* <Button
        handleClick={handlePayment}
        color="Primary"
        size="full"
        radius="m"
        text= "결제하기"
      /> */}
      <Navbar />
    </CartOrderPageLayout>
  );
}

export default CartOrderPage;
