import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackSpace from 'components/atoms/BackSpace';
import { AddMenuLayout }  from './style';
import Button from 'components/atoms/Button';
import Navbar from '../Navbar';

function AddMenuForm() {
  const location = useLocation();
  const navigate = useNavigate();
  const { menuId, menuName, menuDescription, menuPrice, menuImageUrl } = location.state;
  const [quantity, setQuantity] = useState(1);
  
  const increaseQuantity = () => setQuantity(quantity + 1);
  const decreaseQuantity = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  const handleAddToCart = () => {
    // 로직을 추가하여 장바구니에 추가
  };

  return (
    <AddMenuLayout>
      <div style={{ flexGrow: 1 }}>
        <div className="header">
          <BackSpace/>
        </div>
        <img className="headerImage" src={menuImageUrl} alt={menuName} />
        <div className="storeInfo">

          <div className="foodTruckName">
            <span>{menuName}</span>
          </div>

          <div className="location">
            <p>{menuDescription}</p>
          </div>

          <p className="location">가격: {menuPrice}원</p>

          <div className="quantity">
            <button onClick={decreaseQuantity}>-</button>
            <span>{quantity}</span>
            <button onClick={increaseQuantity}>+</button>
          </div>

          <p  className="quantity">총 금액: {menuPrice * quantity}원</p>
          <Button size='full' radius='m' color='Primary' text='담기' handleClick={handleAddToCart}></Button>
        </div>
      </div>
      <Navbar></Navbar>
    </AddMenuLayout>
  );
}

export default AddMenuForm;
