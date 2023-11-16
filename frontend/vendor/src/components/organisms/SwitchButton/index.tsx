// import React, { ReactNode, useEffect, useState } from 'react';
import Button from "components/atoms/Button";
import { SwitchButtonContainer } from "./style";

interface ISwitchButtonProps {
  selectedButton: number;
  setSelectedButton: (value: number) => void;
  firstButton: string;
  secondButton: string;
  position?: string;
}

function SwitchButton({
  selectedButton,
  setSelectedButton,
  firstButton,
  secondButton,
  position,
}: ISwitchButtonProps) {
  const handleClick = (buttonNumber: number) => {
    setSelectedButton(buttonNumber);
  };

  return (
    <SwitchButtonContainer $position={position}>
      <Button
        size="m"
        radius="l"
        color={selectedButton === 1 ? "Primary" : "SubFirst"}
        text={firstButton}
        handleClick={() => handleClick(1)}
      />
      <Button
        size="m"
        radius="l"
        color={selectedButton === 2 ? "Primary" : "SubFirst"}
        text={secondButton}
        handleClick={() => handleClick(2)}
      />
    </SwitchButtonContainer>
  );
}

export default SwitchButton;
