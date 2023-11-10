import { FoodTruckModifyLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import FoodTruckModifyForm from 'components/organisms/FoodTruckModifyForm';
import Navbar from "components/organisms/Navbar";
import { useLocation } from 'react-router-dom';
// import { useEffect } from 'react';

function FoodTruckModifyPage() {
  const location = useLocation();
  const { foodTruck } = location.state || {}; // state에서 foodTruck 데이터 추출

  return (
    <FoodTruckModifyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>푸드트럭 수정</p>
      </div>
      {/* FoodTruckModifyForm에 foodTruck 데이터를 props로 전달 */}
      <FoodTruckModifyForm foodTruck={foodTruck}></FoodTruckModifyForm>
      <Navbar/>
    </FoodTruckModifyLayout>
  );
}

export default FoodTruckModifyPage;
