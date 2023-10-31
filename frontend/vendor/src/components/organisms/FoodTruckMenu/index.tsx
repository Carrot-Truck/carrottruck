import React, { ReactNode, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { FoodTruckMenuContainer } from './style';
import FoodTruckMenuItem from '../../atoms/FoodTruckMenuItem';

interface IFoodTruckMenuProps {}

function FoodTruckMenu(props: IFoodTruckMenuProps) {
  return (
    <FoodTruckMenuContainer>
      <FoodTruckMenuItem></FoodTruckMenuItem>
    </FoodTruckMenuContainer>
  );
}

export default FoodTruckMenu;
