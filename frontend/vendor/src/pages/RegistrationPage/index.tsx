import { useState, useEffect } from 'react';
import { BusinessRegistrationLayout } from './style';
import SwitchButton from 'components/organisms/RegisterSwitchButton';
import BusinessRegistration from 'components/organisms/BusinessRegistration';
import FoodTruckRegistrationForm from 'components/organisms/FoodTruckRegistration';
import BackSpace from 'components/atoms/BackSpace';
import axios from 'axios';

function RegistrationPage() {
  const [selectedButton, setSelectedButton] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const accessToken = localStorage.getItem('accessToken');
  const grantType = localStorage.getItem('grantType');
  const APPLICATION_SPRING_SERVER_URL =
  process.env.NODE_ENV === 'production' ? 'https://k9c211.p.ssafy.io/api' : 'http://localhost:8001/api';

  // 페이지 로딩 시 API 요청 보내기
  useEffect(() => {
    async function fetchData() {
      try {
        const validToken = await axios.get(`${APPLICATION_SPRING_SERVER_URL}/member/vendor-info`,
        {
          headers : {
            Authorization: `${grantType} ${accessToken}`,
          },
        });
        console.log(validToken.data);  
      } catch (error) {
        setIsLoading(true);
      }
    }
    fetchData();
  }, []);

  return (
    <BusinessRegistrationLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>환영해요!</p>
      </div>
      <SwitchButton
        selectedButton={selectedButton}
        setSelectedButton={setSelectedButton}
        firstButton="사업자인증"
        secondButton="푸드트럭등록"
        disabled={isLoading} // isLoading 값에 따라 버튼 활성화 여부 조절
      ></SwitchButton>
      {selectedButton === 1 && <BusinessRegistration />}
      {selectedButton === 2 && <FoodTruckRegistrationForm />}
    </BusinessRegistrationLayout>
  );
}

export default RegistrationPage;
