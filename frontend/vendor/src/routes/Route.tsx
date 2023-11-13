// import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "pages/MainPage";
import LoginPage from "pages/LoginPage";
import RegistrationPage from "pages/RegistrationPage";
import FoodTruckPage from "pages/FoodTruckPage";
import AnalysisPage from "pages/AnalysisPage";
import StatisticsDetailPage from "pages/StatisticsDetailPage";
import StatisticsPage from "pages/StatisticsPage";
import SurveyPage from "pages/SurveyPage";
import SchedulePage from "pages/SchedulePage";
import FoodTruckModifyPage from "pages/FoodTruckModifyPage";
import FoodMenuModifyPage from "pages/FoodMenuModifyPage";
import FoodMenuDetailModifyPage from "pages/FoodMenuDetailModifyPage";
// import FoodMenuDetailPage from 'pages/FoodMenuDetailPage'

function RouteLink() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/registration" element={<RegistrationPage />} />
        <Route path="/foodtruck" element={<FoodTruckPage />} />
        {/* <Route path="/foodtruck/menu/:menuId" element={<FoodMenuDetailPage />} /> */}
        <Route path="/analysis" element={<AnalysisPage />} />
        <Route path="/statistics/detail" element={<StatisticsDetailPage />} />
        <Route path="/statistics" element={<StatisticsPage />} />
        <Route path="/survey" element={<SurveyPage />} />
        <Route path="/schedule" element={<SchedulePage />} />
        <Route path="/foodtruck/modify" element={<FoodTruckModifyPage />} />
        <Route path="/foodtruck/menu/modify" element={<FoodMenuModifyPage />} />
        <Route path="/foodtruck/menu/create" element={<FoodMenuDetailModifyPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default RouteLink;
