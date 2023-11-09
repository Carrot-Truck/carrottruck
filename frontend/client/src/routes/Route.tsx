// import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
// import MainPage from 'pages/MainPage';
// import LoginPage from 'pages/LoginPage';
// import FoodTruckPage from 'pages/FoodTruckPage';
// import FoodMenuDetailPage from 'pages/FoodMenuDetailPage';

function RouteLink() {
  return (
    <BrowserRouter>
      <Routes>
        {/* <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/foodtruck" element={<FoodTruckPage />} />
        <Route path="/foodtruck/menu/:menuId" element={<FoodMenuDetailPage />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default RouteLink;
