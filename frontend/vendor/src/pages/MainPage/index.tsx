import { MainPageLayout } from './style';
import { useEffect, useState } from 'react';
import Navbar from 'components/organisms/Navbar';
import RegistFoodTruckButton from 'components/organisms/RegistFoodTruckButton'
import VendorMainForm from 'components/organisms/VendorMainForm'
import { AxiosError } from 'axios';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function MainPage() {
   // 데이터가 비어있는지 여부를 확인할 상태 변수를 정의합니다.
   const [dataEmpty, setDataEmpty] = useState(true); // 초기값은 true로 설정합니다.
   const navigate = useNavigate();
   const accessToken = localStorage.getItem('accessToken');
   const grantType = localStorage.getItem('grantType');
   const APPLICATION_SPRING_SERVER_URL =
   process.env.NODE_ENV === 'production' ? 'https://j9c107.p.ssafy.io' : 'http://localhost:8001';

   useEffect (()=>{
    const isValidUser = async () => {
      try {
        const validToken = await axios.get(`${APPLICATION_SPRING_SERVER_URL}/member/vendor/info`,
        {
          headers : {
            Authorization: `${grantType} ${accessToken}`,
          },
        });
        console.log(validToken);
        return true;
      } catch (error) {
        const err = error as AxiosError;
        if(err.response?.status === 401){
           alert('로그인이 필요합니다.');
          return navigate('/login'); 
        }else{
          console.error('Error!!', error);
          alert("알 수 없는 오류가 발생하였습니다. \n다시 로그인 해주세요.");
          return navigate('/login'); // 리다이렉트하고 함수 종료
        }
      }
    };
    const hasVendorFoodTruck = async() => {
        //TODO: 하기 코드를 token 검증 API 나오면 그 API로 교체해야함.
      try{
        const response = await axios.get(`${APPLICATION_SPRING_SERVER_URL}/food-truck/overview?lastFoodTruckId=`,
        {
          headers: {
            Authorization: `${grantType} ${accessToken}`,
          },
        });
        if (response.data.success) { // 정보를 받았고,
          setDataEmpty(response.data.items.length === 0); // 그 정보가 비어있다면 true / 비어있지 않다면 false
          console.log(response.data.items)
        }
      }catch(error){
        console.log("error", error);
        alert('처리 중 에러 발생!');
        navigate('/login');
      }
    };
    
    // isValidUser 함수를 비동기로 호출하고 결과를 확인합니다.
    isValidUser().then((isValid) => {
      if (isValid) {
        hasVendorFoodTruck();
      }
    });
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
