import { CartMenuItemWrapper, MenuTextWrapper } from "./style";
import { AxiosResponse } from "axios";
import { removeCartMenu } from "api/cart";


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
}

function CartMenuItem({ menu }: ICartMenuItemProp) {

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
          <button>-</button>
          <p>{menu.cartMenuQuantity}</p>
          <button>+</button>
        </div>
        <p>{menu.cartMenuTotalPrice * menu.cartMenuQuantity}원</p>
      </MenuTextWrapper>
      <button>X</button>
    </CartMenuItemWrapper>
  );
}

export default CartMenuItem;
