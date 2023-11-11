// import { useState } from 'react';
import { useNavigate } from 'react-router';
import { FoodMenuModifyLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import Button from 'components/atoms/Button';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';
import PlusButton from 'assets/icons/plus_button.svg';

function FoodMenuModifyPage() {
  const menus = [
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
  ];

  const navigate = useNavigate();
  const menuModify = () => {
    navigate(-1);
  };
  const createMenu = () => {};

  return (
    <FoodMenuModifyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>메뉴 수정</p>
      </div>
      <FoodTruckMenu menus={menus} />
      <img className="plusButton" src={PlusButton} onClick={createMenu} alt="" />
      <Button text="수정 완료" color="Primary" size="full" radius="s" handleClick={menuModify} />
    </FoodMenuModifyLayout>
  );
}

export default FoodMenuModifyPage;
