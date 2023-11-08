import { useState, useEffect } from 'react';
import { BusinessRegistrationContainer } from './style';
import { useNavigate } from 'react-router-dom';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import axios from 'axios';

function BusinessRegistration() {
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [businessNumber, setBusinessNumber] = useState('');
  const [owner, setOwner] = useState('');
  const accessToken = localStorage.getItem('accessToken');
  const grantType = localStorage.getItem('grantType');
  const [phoneNumber, setPhoneNumber] = useState('');
  const APPLICATION_SPRING_SERVER_URL =
  process.env.NODE_ENV === 'production' ? 'https://k9c211.p.ssafy.io/api' : 'http://localhost:8001/api';

  const authentication = async () => {
    try {
      
      const body = {
        "tradeName": name,
        "businessNumber": businessNumber,
        "vendorName": owner,
        "phoneNumber": phoneNumber
      }
      const response = await axios.post(`${APPLICATION_SPRING_SERVER_URL}/member/vendor-info`, 
      body,
      {
        headers : {
          Authorization: `${grantType} ${accessToken}`,
        },
      }
      );
      
      if(response.data.code === 201){
        alert("사업자 등록 성공!");
        navigate('/');
      }
    } catch (error) {
      alert('사업자 등록 중 에러 발생!\n관리자에게 문의하세요.');
      navigate('/');
    }
  };

  useEffect(() => {
    if (name && businessNumber && owner) {
      // setIsDone(true);
    } else {
      // setIsDone(false);
    }
  }, [name, businessNumber, owner]);
  return (
    <BusinessRegistrationContainer>
      <div className="registration-form">
        <div className="input">
          <span>상호명</span>
          <Input placeholder="상호명을 입력해주세요" value={name} setValue={setName} type="text" />
        </div>
        <div className="field">
          <span>사업자등록번호</span>
          <Input placeholder="숫자만 입력해주세요" value={businessNumber} setValue={setBusinessNumber} type="number" />
        </div>
        <div className="field">
          <span>대표자명</span>
          <Input placeholder="대표자명을 입력해주세요" value={owner} setValue={setOwner} type="text" />
        </div>
        <div className="field">
          <span>사업자 전화번호</span>
          <Input placeholder="사업자 전화번호를 입력해주세요" value={phoneNumber} setValue={setPhoneNumber} type="number" />
        </div>
      </div>
      <Button text="인증" color="Primary" size="full" radius="s" handleClick={authentication} />
    </BusinessRegistrationContainer>
  );
}

export default BusinessRegistration;
