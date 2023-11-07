// import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MainPage from 'pages/MainPage';
import LoginPage from 'pages/LoginPage';
import RegistrationPage from 'pages/BusinessRegistrationPage';

function RouteLink() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/registration" element={<RegistrationPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default RouteLink;
