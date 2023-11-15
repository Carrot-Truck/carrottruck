import { getCart } from "api/cart";
import { AxiosResponse } from "axios";
import BackSpace from "components/atoms/BackHome";
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
    totalPrice: number;
    cartMenus: {
      cartMenuId: "";
      menuName: "";
      menuPrice: 0;
      cartMenuTotalPrice: 0;
      cartMenuQuantity: number;
      menuImageUrl: "";
      cartMenuOptionDtos: {
        menuOptionName: "";
        menuOptionPrice: 0;
      }[];
    }[];
  }>(null);

  const isDisabled = !cart;

  useEffect(() => {
    const fetchData = async () => {
      getCart(
        (response: AxiosResponse) => {
          const data = getData(response);
          console.log(data);
          setCart(data);
        },
        (error: any) => {
          alert("잘못된 접근입니다");
          navigate("/login");
        }
      );
    };
    fetchData();
  }, []);

  const handleMenuRemoved = (removedMenuId: string, removedPrice: number) => {
    setCart((prevCart) => {
      if (prevCart) {
        const newCartMenus = prevCart.cartMenus.filter(
          (menu) => menu.cartMenuId !== removedMenuId
        );
        const newTotalPrice = prevCart.totalPrice - removedPrice;

        return {
          ...prevCart,
          cartMenus: newCartMenus,
          totalPrice: newTotalPrice,
        };
      }
      return prevCart;
    });
  };

  const handleMenuUpdated = (updatedMenuId: string, newQuantity: number) => {
    setCart((prevCart) => {
      if (prevCart) {
        const newCartMenus = prevCart.cartMenus.map((menu) =>
          menu.cartMenuId === updatedMenuId
            ? { ...menu, cartMenuQuantity: newQuantity }
            : menu
        );

        // 새로운 총 금액 계산
        const newTotalPrice = newCartMenus.reduce(
          (sum, item) => sum + item.menuPrice * item.cartMenuQuantity,
          0
        );

        return {
          ...prevCart,
          cartMenus: newCartMenus,
          totalPrice: newTotalPrice,
        };
      }
      return prevCart;
    });
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

  const handleCartOrder = () => {
    navigate("/cartorder"); // 결제 페이지로 이동
  };

  return (
    <CartPageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>장바구니</p>
      </div>
      <div>{cart?.foodTruckName}</div>
      {renderCartContent()}
      <div>{cart?.totalPrice}</div>
      <Button
        handleClick={handleCartOrder}
        color={isDisabled ? "SubFirst" : "Primary"}
        size="full"
        radius="m"
        text="포장 주문하기"
        disabled={isDisabled}
      />
      <Navbar />
    </CartPageLayout>
  );
}

export default CartPage;
