import { useNavigate, useLocation } from 'react-router-dom';
import { FoodTruckMenuItemWrapper, MenuTextWrapper } from './style';
import logo from '../../../assets/icons/delete.svg';
import { deleteMenu } from 'api/menu';

interface IFoodTruckMenuItemProps {
  menuId: number;
  menuName: string;
  menuPrice: number;
  menuDescription: string;
  menuSoldOut: boolean;
  menuImageUrl: string;
}

function FoodTruckMenuItem(props: IFoodTruckMenuItemProps) {
  const { menuId, menuName, menuDescription, menuPrice, menuImageUrl } = props;
  const navigate = useNavigate();
  const location = useLocation(); // 현재 위치 정보를 가져옵니다.

  const handleFail = () => {
    alert("삭제되었습니다.");
    navigate(0);
  } 

  const clickDeleteMenu = () => {
    if(window.confirm('정말 삭제하시겠습니까?')){
      deleteMenu(menuId, null, handleFail);
    }
  }

  // 현재 URL이 '/foodtruck/menu/modify' 일 때만 deleteIcon을 표시합니다.
  const showDeleteIcon = location.pathname === '/foodtruck/menu/modify';

  return (
    <FoodTruckMenuItemWrapper>
      <img placeholder="이미지입니다." alt={`${menuName} 이미지`} src={menuImageUrl}></img>
      <MenuTextWrapper>
        <p>{menuName}</p>
        <p>{menuDescription}</p>
        <p>{menuPrice}원</p>
      </MenuTextWrapper>
      {showDeleteIcon && <img className="deleteIcon" src={logo} alt="삭제 아이콘" onClick={clickDeleteMenu}/>}
    </FoodTruckMenuItemWrapper>
  );
}

export default FoodTruckMenuItem;
