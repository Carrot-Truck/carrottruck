// import React, { ReactNode, useEffect, useState } from 'react';
// import { useLocation } from 'react-router-dom';
import { FoodTruckMenuItemWrapper, MenuTextWrapper } from './style';
import logo from '../../../assets/imgs/playstore.png';

// interface IFoodTruckMenuItemProps {
//   img?: object;
//   menuName: string;
//   menuDiscription: string;
//   menuPrice: number;
// }

// function FoodTruckMenuItem(props: IFoodTruckMenuItemProps) {
function FoodTruckMenuItem() {
  // const { img, menuName, menuDiscription, menuPrice } = props;
  const menuName = '달콤짭짤한 밥도둑 된장 삼겹 구이';
  const menuDiscription = '동현 된장삼겹의 시그니쳐. 오직 된장 삽겹살 구이만!';
  const menuPrice = 8900;

  return (
    <FoodTruckMenuItemWrapper>
      <img placeholder="이미지입니다." alt="~" src={logo}></img>
      <MenuTextWrapper>
        <p>{menuName}</p>
        <p>{menuDiscription}</p>
        <p>{menuPrice}원</p>
      </MenuTextWrapper>
    </FoodTruckMenuItemWrapper>
  );
}

export default FoodTruckMenuItem;
