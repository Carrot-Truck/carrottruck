import { CartMenuItemWrapper, MenuTextWrapper } from "./style";
import { AxiosResponse } from "axios";
import { incrementCartMenu, decrementCartMenu, removeCartMenu } from "api/cart";
import { useNavigate } from "react-router-dom";

interface ICartMenuOption {
  menuOptionName: string;
  menuOptionPrice: number;
}

// 카트 메뉴 아이템 인터페이스 정의
interface ICartMenu {
  cartMenuId: string;
  menuName: string;
  menuPrice: number;
  cartMenuTotalPrice: number;
  cartMenuQuantity: number;
  menuImageUrl: string;
  cartMenuOptionDtos: ICartMenuOption[];
}

interface ICartMenuItemProp {
  menu: ICartMenu; // menus 프로퍼 티의 타입을 Menu[]로 정의
  onItemRemoved: (cartMenuId: string) => void;
  onItemUpdated: (cartMenuId: string, newQuantity: number) => void
}

function CartMenuItem({ menu, onItemRemoved, onItemUpdated }: ICartMenuItemProp) {
  const navigate = useNavigate();
  const handleDelete = async () => {
    removeCartMenu(
      menu.cartMenuId,
      () => {},
      (error: any) => {
        onItemRemoved(menu.cartMenuId);
        console.log("삭제 성공");
      }
    );
  };

  const handleIncrement = async () => {
    incrementCartMenu(
      menu.cartMenuId,
      () => {
        onItemUpdated(menu.cartMenuId, menu.cartMenuQuantity + 1);
        console.log("수량 증가 성공");
      },
      (error: any) => {
        console.log("수량 증가 실패", error);
      }
    );
  };

  const handleDecrement = async () => {
    if (menu.cartMenuQuantity > 1) {
      decrementCartMenu(
        menu.cartMenuId,
        () => {
          onItemUpdated(menu.cartMenuId, menu.cartMenuQuantity - 1);
          console.log("수량 감소 성공");
        },
        (error: any) => {
          console.error("수량 감소 실패", error);
        }
      );
    }
  };

  return (
    <CartMenuItemWrapper>
      <img
        placeholder="이미지입니다."
        alt={`이미지`}
        src={menu.menuImageUrl}
      ></img>
      <MenuTextWrapper>
        <p>{menu.menuName}</p>
        <div>
          <button onClick={handleIncrement}>-</button>
          <p>{menu.cartMenuQuantity}</p>
          <button onClick={handleDecrement}>+</button>
        </div>
        <p>{menu.cartMenuTotalPrice * menu.cartMenuQuantity}원</p>
      </MenuTextWrapper>
      <button onClick={handleDelete}>X</button>
    </CartMenuItemWrapper>
  );
}

export default CartMenuItem;
