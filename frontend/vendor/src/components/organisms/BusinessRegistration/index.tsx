import { useState, useEffect } from 'react';
import { BusinessRegistrationContainer } from './style';
import Input from 'components/atoms/Input';

function BusinessRegistration() {
  const [name, setName] = useState('');
  const [businessNumber, setBusinessNumber] = useState('');
  const [owner, setOwner] = useState('');

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
          <Input placeholder="상호명을 입력해주세요 ㅅㅂ" value={name} setValue={setName} type="text" />
        </div>
        <div className="field">
          <span>사업자등록번호</span>
          <Input
            placeholder="숫자만 입력해주세요 ㅅㅂ"
            value={businessNumber}
            setValue={setBusinessNumber}
            type="text"
          />
        </div>
        <div className="field">
          <span>대표자명</span>
          <Input placeholder="대표자명을 입력해주세요" value={owner} setValue={setOwner} type="text" />
        </div>
      </div>
    </BusinessRegistrationContainer>
  );
}

export default BusinessRegistration;
