import React, { useState } from 'react';
import { LoginPageLayout } from './style';
import Button from 'components/atoms/Button';
import SwitchButton from 'components/organisms/SwitchButton';
import useMovePage from 'hooks/useMovePage';

function LoginPage() {
  const [movePage] = useMovePage();
  const handleBackClick = () => {
    movePage('/vendor');
  };

  return (
    <LoginPageLayout>
      <Button size="s" radius="s" color="Normal" handleClick={() => handleBackClick()}>
        뒤로가기
      </Button>
      <SwitchButton></SwitchButton>
    </LoginPageLayout>
  );
}

export default LoginPage;
