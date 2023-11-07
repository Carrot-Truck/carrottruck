import { useState, useEffect } from 'react';
import { BusinessRegistrationContainer } from './style';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';

function BusinessRegistration() {
  const [name, setName] = useState('');
  const [businessNumber, setBusinessNumber] = useState('');
  const [owner, setOwner] = useState('');

  const authentication = async () => {
    try {
    } catch (error) {
      console.error('너 에러났어...:', error);
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
          <Input placeholder="숫자만 입력해주세요" value={businessNumber} setValue={setBusinessNumber} type="text" />
        </div>
        <div className="field">
          <span>대표자명</span>
          <Input placeholder="대표자명을 입력해주세요" value={owner} setValue={setOwner} type="text" />
        </div>
      </div>
      <Button text="인증" color="Primary" size="full" radius="s" handleClick={authentication} />
    </BusinessRegistrationContainer>
  );
}

export default BusinessRegistration;
