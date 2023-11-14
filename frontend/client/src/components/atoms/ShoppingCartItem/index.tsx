import React from 'react';
import ShoppingCart from '../../../assets/icons/shopping_cart.svg'; // SVG 파일 경로에 맞게 수정
import { ShoppingCartContainer } from "./style";
import { useNavigate } from 'react-router-dom';

const ShoppingCartItem = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/cart');
  };

  return (
    <ShoppingCartContainer onClick={handleClick}>
      <img src={ShoppingCart} alt="Shopping Cart" />
    </ShoppingCartContainer>
  );
};

export default ShoppingCartItem;