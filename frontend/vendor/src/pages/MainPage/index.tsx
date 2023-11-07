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
   const accessToken = localStorage.getItem('accessToken');
   const grantType = localStorage.getItem('grantType');

   useEffect (()=>{
    const getDataL = async () => {
      try {
        //TODO: 하기 코드를 token 검증 API 나오면 그 API로 교체해야함.
        if(localStorage.getItem('accessToken') !== null){ // 로그인 한 사용자라면
          try{
            const response = await axios.get('http://localhost:8001/food-truck/overview?lastFoodTruckId=',
            {
              headers: {
                Authorization: `${grantType} ${accessToken}`,
              },
            });
            if (response.data.success) { // 정보를 받았고,
              setDataEmpty(response.data.items.length === 0); // 그 정보가 비어있다면 true / 비어있지 않다면 false
              console.log(response.data.items)
            }
          } catch(error){
            
          }
        }else{
          alert("로그인이 필요한 서비스입니다.");
          navigate('/login');
        }
      } catch (error) {
        console.error('Error!!', error);
        alert("처리중 오류 발생! 다시 시도해주세요.");
        navigate('/');
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
