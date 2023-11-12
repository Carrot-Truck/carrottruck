import { getCart } from "api/cart";
import { AxiosResponse } from "axios";
import BackSpace from "components/atoms/BackSpace";
import CartMenu from "components/organisms/CartMenu";
import { useEffect, useState } from "react";
import { CartPageLayout } from "./style";
import Navbar from "components/organisms/Navbar";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router-dom";

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function CartPage() {
  const navigate = useNavigate();
  const [cart, setCart] = useState<null | {
    foodTruckName: "";
    totalPrice: 0;
    cartMenus: {
      cartMenuId: "";
      menuName: "";
      menuPrice: 0;
      cartMenuTotalPrice: 0;
      cartMenuQuantity: 0;
      menuImageUrl: "";
      cartMenuOptionDtos: {
        menuOptionName: "";
        menuOptionPrice: 0;
      }[];
    }[];
  }>(null);

  useEffect(() => {
    const fetchData = async () => {
      getCart(
        (response: AxiosResponse) => {
          const data = getData(response);
          console.log(data);
          setCart(data);
        },
        (error: any) => {
          setCart(null);
        }
      );
    };
    fetchData();
  }, []);

  const handleMenuRemoved = (removedMenuId: string) => {
    setCart(
      (prevCart) =>
        prevCart && {
          ...prevCart,
          cartMenus: prevCart.cartMenus.filter(
            (menu) => menu.cartMenuId !== removedMenuId
          ),
        }
    );
  };

  const handleMenuUpdated = (updatedMenuId: string, newQuantity: number) => {
    setCart(
      (prevCart) =>
        prevCart && {
          ...prevCart,
          cartMenus: prevCart.cartMenus.map((menu: any) =>
            menu.cartMenuId === updatedMenuId
              ? { ...menu, cartMenuQuantity: newQuantity }
              : menu
          ),
        }
    );
  };

  const renderCartContent = () => {
    if (!cart || cart.cartMenus.length === 0) {
      return <p>텅</p>;
      // 텅 화면 만들기
    }
    return (
      <CartMenu
        menus={cart.cartMenus}
        onMenuRemoved={handleMenuRemoved}
        onMenuUpdated={handleMenuUpdated}
      />
    );
  };

  const handleCheckout = () => {
    navigate('/cartorder'); // 결제 페이지로 이동
  };

  return (
    <CartPageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>장바구니</p>
      </div>
      <div>
        {cart?.foodTruckName}
      </div>
      {renderCartContent()}
      <div>
        {cart?.totalPrice}
      </div>
      <Button
        handleClick={handleCheckout}
        color="Primary"
        size="full"
        radius="m"
        text= "결제하기"
      />
      <Navbar/>
    </CartPageLayout>
  );
}

export default CartPage;
