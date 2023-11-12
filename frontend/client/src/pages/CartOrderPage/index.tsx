import BackSpace from "components/atoms/BackSpace";
import { CartOrderPageLayout } from "./style";
import Navbar from 'components/organisms/Navbar';
import { AxiosResponse } from "axios";
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
  }, []);

  const handlePayment = () => {
    navigate('/'); // 결제 페이지로 이동
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
        handleClick={handlePayment}
        color="Primary"
        size="full"
        radius="m"
        text= "결제하기"
      />
      <Navbar/>
    </CartOrderPageLayout>
  );
}

export default CartOrderPage;
