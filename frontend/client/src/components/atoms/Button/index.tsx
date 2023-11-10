// import React from 'react';
import { ButtonWrapper } from './style';

interface IButtonProps {
  size: 's' | 'm' | 'l' | 'full';
  radius: 's' | 'm' | 'l';
  color: 'Normal' | 'Primary' | 'Danger' | 'Success' | 'SubFirst' | 'SubSecond';
  text: string;
  handleClick: () => void;
  disabled?: boolean;
}

function Button(props: IButtonProps) {
  const { size, radius, color, text, handleClick, disabled } = props;

  return (
    <ButtonWrapper $size={size} $radius={radius} $color={color} onClick={handleClick} disabled={disabled ?? false}>
      {text}
    </ButtonWrapper>
  );
}

export default Button;
