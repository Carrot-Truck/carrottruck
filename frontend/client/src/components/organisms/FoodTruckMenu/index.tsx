// import React from 'react';
// import { useLocation } from 'react-router-dom';
import { FoodTruckMenuContainer } from './style';
import FoodTruckMenuItem from '../../atoms/FoodTruckMenuItem';

// 메뉴 아이템 인터페이스를 정의합니다.
interface Menu {
  menuId: number;
  menuName: string;
  menuPrice: number;
  menuDescription: string;
  menuSoldOut: boolean;
  menuImageId: number;
}

interface IFoodTruckMenuProps {
  menus: Menu[]; // menus 프로퍼티의 타입을 Menu[]로 정의
}

function FoodTruckMenu({ menus }: IFoodTruckMenuProps) {
  return (
    <FoodTruckMenuContainer>
      {menus.map((menu) => (
        <FoodTruckMenuItem key={menu.menuId} {...menu} />
      ))}
    </FoodTruckMenuContainer>
  );
}

export default FoodTruckMenu;
