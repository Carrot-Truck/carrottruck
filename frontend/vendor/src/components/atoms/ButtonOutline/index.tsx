// import React from 'react';
import { ButtonOutlineWrapper } from "./style";

interface IButtonOutlineProps {
  size: "s" | "m" | "l" | "full";
  radius: "s" | "m" | "l";
  color: "Normal" | "Primary" | "Danger" | "Success" | "SubFirst" | "SubSecond";
  text: string;
  handleClick: () => void;
  disabled?: boolean;
}

function ButtonOutline(props: IButtonOutlineProps) {
  const { size, radius, color, text, handleClick, disabled } = props;

  return (
    <ButtonOutlineWrapper
      $size={size}
      $radius={radius}
      $color={color}
      onClick={handleClick}
      disabled={disabled ?? false}
    >
      {text}
    </ButtonOutlineWrapper>
  );
}

export default ButtonOutline;
