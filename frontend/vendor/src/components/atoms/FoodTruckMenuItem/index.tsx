// import React, { ReactNode, useEffect, useState } from 'react';
// import { useLocation } from 'react-router-dom';
import { FoodTruckMenuItemWrapper, MenuTextWrapper } from './style';
import { useNavigate } from 'react-router-dom';
import logo from '../../../assets/icons/delete.svg';
import { deleteMenu } from 'api/menu';
// import { AxiosError } from 'axios';

// 메뉴 아이템 인터페이스를 정의.
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

  // const handleClick = () => {
  //   // URL 템플릿에 맞게 동적으로 menuId 값을 삽입하여 이동
  //   navigate(`/foodtruck/menu/${menuId}`);
  // };

  const handleFail = () => {
    alert("삭제되었습니다.");
    navigate(0);
  } 

  const clickDeleteMenu = () => {
    if(window.confirm('정말 삭제하시겠습니까?')){
      deleteMenu(menuId, null, handleFail);
    }
  }

  return (
    <FoodTruckMenuItemWrapper >
      <img placeholder="이미지입니다." alt={`${menuName} 이미지`} src={menuImageUrl}></img>
      <MenuTextWrapper>
        <p>{menuName}</p>
        <p>{menuDescription}</p>
        <p>{menuPrice}원</p>
      </MenuTextWrapper>
      <img className="deleteIcon" src={logo} alt="" onClick={clickDeleteMenu}/>
    </FoodTruckMenuItemWrapper>
  );
}

export default FoodTruckMenuItem;
