import { useState } from 'react';
import { LoginPageLayout } from './style';
import SwitchButton from 'components/organisms/SwitchButton';
import BackSpace from 'components/atoms/BackSpace';
import LoginForm from 'components/organisms/LoginForm';
import JoinForm from 'components/organisms/JoinForm';

function LoginPage() {
  const [selectedButton, setSelectedButton] = useState(1);

  return (
    <LoginPageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>환영해요!</p>
      </div>
      <SwitchButton
        selectedButton={selectedButton}
        setSelectedButton={setSelectedButton}
        firstButton="로그인"
        secondButton="회원가입"
      ></SwitchButton>
      {selectedButton === 1 && <LoginForm />}
      {selectedButton === 2 && <JoinForm />}
    </LoginPageLayout>
  );
}

export default LoginPage;
