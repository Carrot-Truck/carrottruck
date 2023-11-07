import { MainPageLayout } from './style';
import { useEffect, useState } from 'react';
import Navbar from 'components/organisms/Navbar';
import RegistFoodTruckButton from 'components/organisms/RegistFoodTruckButton'
import VendorMainForm from 'components/organisms/VendorMainForm'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function MainPage() {
   // 데이터가 비어있는지 여부를 확인할 상태 변수를 정의합니다.
   const [dataEmpty, setDataEmpty] = useState(true); // 초기값은 true로 설정합니다.
   const navigate = useNavigate();

   useEffect (()=>{
    const getDataL = async () => {
      try {
        const response = await axios.get('http://localhost:8001/food-truck/overview?lastFoodTruckId=');
        if (response.data.success) {
          setDataEmpty(response.data.items.length === 0);
        }
      } catch (error) {
        console.error('Error!!', error);
        alert("로그인이 필요합니다.");
        navigate('/login');
      }
    };
    getDataL();
   }, []);

  return (
    <MainPageLayout>
      {dataEmpty ? ( // 데이터가 비어 있으면 RegistFoodTruckButton을 표시합니다.
        <RegistFoodTruckButton />
      ) : ( // 데이터가 비어 있지 않으면 VendorForm을 표시합니다.
        <VendorMainForm /> 
      )}
      <Navbar></Navbar>
    </MainPageLayout>
  );
}

export default MainPage;
