// import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MainPage from 'pages/MainPage';
import LoginPage from 'pages/LoginPage';
// import FoodTruckPage from 'pages/FoodTruckPage';
// import FoodMenuDetailPage from 'pages/FoodMenuDetailPage';
import CartPage from 'pages/CartPage';
import MyPage from 'pages/MyPage';

function RouteLink() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/cart" element={<CartPage />} />
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/mypage/detail" element={<MyPage />} />
        {/* <Route path="/foodtruck" element={<FoodTruckPage />} /> */}
        {/* <Route path="/foodtruck/menu/:menuId" element={<FoodMenuDetailPage />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default RouteLink;
