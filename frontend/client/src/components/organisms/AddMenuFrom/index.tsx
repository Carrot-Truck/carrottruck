import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackSpace from 'components/atoms/BackSpace';
import { AddMenuLayout }  from './style';
import Button from 'components/atoms/Button';
import Navbar from '../Navbar';
import { createCart } from 'api/cart';
import { AxiosResponse } from 'axios';

function AddMenuForm() {
  const location = useLocation();
  const navigate = useNavigate();
  const { menuId, menuName, menuDescription, menuPrice, menuImageUrl } = location.state;
  const [cartMenuQuantity, setCartMenuQuantity] = useState(1);
  
  const increaseQuantity = () => setCartMenuQuantity(cartMenuQuantity + 1);
  const decreaseQuantity = () => {
    if (cartMenuQuantity > 1) {
      setCartMenuQuantity(cartMenuQuantity - 1);
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  const getData = (response: AxiosResponse) => {
    return response.data.data;
  };

  const handleAddToCart = async () => {
    if (menuId) {
      try {
        const requestData = {
          menuId,
          cartMenuQuantity
        };
        // submitSurvey 함수를 호출하여 서버에 데이터 제출
        await createCart(
          requestData,
          (response: AxiosResponse) => {
            const data = getData(response);
            console.log(data);
            alert("메뉴를 담았습니다");
            navigate(-1);
          },
          (error: any) => {
            console.log(requestData);
            console.log(error);
          }
        );
      } catch (error) {
        console.error("메뉴 추가 중 오류 발생:", error);
        alert("메뉴 추가에 실패했습니다.");
      }
    } else {
      // 필요한 데이터가 없는 경우 알림
      alert("메뉴를 확인해 주세요");
    }
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
            <span>{cartMenuQuantity}</span>
            <button onClick={increaseQuantity}>+</button>
          </div>

          <p  className="quantity">총 금액: {menuPrice * cartMenuQuantity}원</p>
          <Button size='full' radius='m' color='Primary' text='담기' handleClick={handleAddToCart}></Button>
        </div>
      </div>
      <Navbar></Navbar>
    </AddMenuLayout>
  );
}

export default AddMenuForm;
