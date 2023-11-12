import { CartMenuItemWrapper, MenuTextWrapper } from "./style";
import { AxiosResponse } from "axios";
import { removeCartMenu } from "api/cart";
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
}

function CartMenuItem({ menu, onItemRemoved }: ICartMenuItemProp) {
  const navigate = useNavigate();
  const handleDelete = async () => {
    removeCartMenu(
      menu.cartMenuId,
      () => {
      },
      (error: any) => {
        onItemRemoved(menu.cartMenuId);
        console.log("삭제 성공");
        // navigate(0);
      }
    );
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
          <button>-</button>
          <p>{menu.cartMenuQuantity}</p>
          <button>+</button>
        </div>
        <p>{menu.cartMenuTotalPrice * menu.cartMenuQuantity}원</p>
      </MenuTextWrapper>
      <button onClick={handleDelete}>X</button>
    </CartMenuItemWrapper>
  );
}

export default CartMenuItem;
