import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { FoodMenuModifyLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import Button from 'components/atoms/Button';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';
import PlusButton from 'assets/icons/plus_button.svg';
import { getMenus } from 'api/menu';
import { useLocation } from 'react-router-dom';
import { AxiosResponse, AxiosError } from 'axios';

function FoodMenuModifyPage() {
  const location = useLocation();
  const { foodTruck } = location.state || {}; // state에서 foodTruck 데이터 추출

  const [menus, setMenu] = useState( [
    {
      menuId: 1,
      menuName: '달콤짭짤한 밥도둑 된장 삼겹살 구이',
      menuPrice: 8900,
      menuDescription: '동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!',
      menuSoldOut: false,
      menuImageUrl: 'imageUrl'
    },
    {
      menuId: 2,
      menuName: '노른자 된장 삼겹살 덮밥',
      menuPrice: 6900,
      menuDescription: '감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥',
      menuSoldOut: false,
      menuImageUrl: 'imageUrl'
    }
  ]);

  const navigate = useNavigate();

  const createNewMenu = () => {
    navigate('/foodtruck/menu/create');
  };

  // const modifySuccess = (response: AxiosResponse) =>{

  // }

  const menuModify = () => {
    alert('수정 완료');
  
    navigate('/foodtruck');
  };
  

  const loadMenu = (response: AxiosResponse) => {
    console.log(response);
    setMenu(response.data.data.menus);
  };

  // const modifyFail = (response: AxiosError) => {
  //   console.log("Error ", response);
  //   alert('메뉴 수정 중 에러 발생!\n다시 시도해주세요.');
  //   navigate('/');
  // }

  const handleFail = (response: AxiosError) => {
    console.log("Error ", response);
    alert('메뉴 조회 중 에러 발생!\n다시 시도해주세요.');
    navigate('/');
  }

  useEffect(()=>{
    getMenus(foodTruck.foodTruckId, loadMenu, handleFail);
  }, []);

  return (
    <FoodMenuModifyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>메뉴 수정</p>
      </div>
      <FoodTruckMenu menus={menus} />
      <img className="plusButton" src={PlusButton} onClick={createNewMenu} alt="" />
      <Button text="수정 완료" color="Primary" size="full" radius="s" handleClick={menuModify} />
    </FoodMenuModifyLayout>
  );
}

export default FoodMenuModifyPage;
