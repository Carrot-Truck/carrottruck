import { CartMenuContainer } from "./style";
import CartMenuItem from "components/atoms/CartMenuItem";
import { AxiosResponse } from "axios";
import { useEffect } from "react";

interface ICartMenuOption {
  menuOptionName: string;
  menuOptionPrice: number;
}

interface ICartMenu {
  cartMenuId: string;
  menuName: string;
  menuPrice: number;
  cartMenuTotalPrice: number;
  cartMenuQuantity: number;
  menuImageUrl: string;
  cartMenuOptionDtos: ICartMenuOption[];
}

interface ICartMenuProps {
  menus: ICartMenu[];
  onMenuRemoved: (cartMenuId: string) => void;  // menus 프로퍼 티의 타입을 Menu[]로 정의
  onMenuUpdated: (cartMenuId: string, newQuantity: number) => void
}

function CartMenu({ menus, onMenuRemoved, onMenuUpdated }: ICartMenuProps) {
  return (
    <CartMenuContainer>
      {menus.map((menu) => (
        <CartMenuItem
        key={menu.cartMenuId}
        menu={menu}
        onItemRemoved={(cartMenuId) => onMenuRemoved(cartMenuId)}
        onItemUpdated={(cartMenuId, newQuantity) => onMenuUpdated(cartMenuId, newQuantity)}
        />
      ))}
    </CartMenuContainer>
  );
}

export default CartMenu;
