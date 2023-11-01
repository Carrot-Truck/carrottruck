import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MainPage from '../pages/MainPage';
import LoginPage from '../pages/LoginPage';

function RouteLink() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/vendor/" element={<MainPage />} />
        <Route path="/vendor/login" element={<LoginPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default RouteLink;
