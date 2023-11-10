// import React from 'react';
import { BigButtonWrapper } from './style';

interface IButtonProps {
  size: 's' | 'm' | 'l' | 'full' | 'big';
  radius: 's' | 'm' | 'l';
  color: 'Normal' | 'Primary' | 'Danger' | 'Success' | 'SubFirst' | 'SubSecond';
  text: string;
  handleClick: () => void;
  disabled?: boolean;
}

function Button(props: IButtonProps) {
  const { size, radius, color, text, handleClick, disabled } = props;

  return (
    <BigButtonWrapper $size={size} $radius={radius} $color={color} onClick={handleClick} disabled={disabled ?? false}>
      {text}
    </BigButtonWrapper>
  );
}

export default Button;
