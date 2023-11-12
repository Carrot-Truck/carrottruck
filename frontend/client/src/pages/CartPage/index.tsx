import { getCart } from "api/cart";
import { AxiosResponse } from "axios";
import BackSpace from "components/atoms/BackSpace";
import CartMenu from "components/organisms/CartMenu";
import { useEffect, useState } from "react";
import { CartPageLayout } from "./style";
import NavbarItem from "components/atoms/NavbarItem";

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function CartPage() {
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


  const renderCartContent = () => {
    if (!cart || cart.cartMenus.length === 0) {
      return <p>텅</p>;
    }
    return <CartMenu menus={cart.cartMenus} onMenuRemoved={handleMenuRemoved} />;
  };



  return (
    <CartPageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>장바구니</p>
      </div>
      {renderCartContent()}
    </CartPageLayout>
  );
}

export default CartPage;
