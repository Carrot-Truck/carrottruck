import React, { useState } from 'react';
import { LoginPageLayout } from './style';
import Button from 'components/atoms/Button';
import SwitchButton from 'components/organisms/SwitchButton';
import useMovePage from 'hooks/useMovePage';
import BackSpace from 'components/atoms/BackSpace';
import JoinForm from 'components/organisms/JoinForm';

function LoginPage() {
  const [movePage] = useMovePage();
  const handleBackClick = () => {
    movePage('/vendor');
  };

  return (
    <LoginPageLayout>
      <BackSpace></BackSpace>
      <SwitchButton></SwitchButton>
      <JoinForm></JoinForm>
    </LoginPageLayout>
  );
}

export default LoginPage;
