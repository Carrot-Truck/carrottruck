// import React, { ReactNode, useEffect, useState } from 'react';
import Button from 'components/atoms/Button';
import { SwitchButtonContainer } from './style';

interface ISwitchButtonProps {
  selectedButton: number;
  setSelectedButton: (value: number) => void;
}

function SwitchButton({ selectedButton, setSelectedButton }: ISwitchButtonProps) {
  const handleClick = (buttonNumber: number) => {
    setSelectedButton(buttonNumber);
  };

  return (
    <SwitchButtonContainer>
      <Button
        size="m"
        radius="l"
        color={selectedButton === 1 ? 'Primary' : 'SubFirst'}
        text="로그인"
        handleClick={() => handleClick(1)}
      />
      <Button
        size="m"
        radius="l"
        color={selectedButton === 2 ? 'Primary' : 'SubFirst'}
        text="회원가입"
        handleClick={() => handleClick(2)}
      />
    </SwitchButtonContainer>
  );
}

export default SwitchButton;
