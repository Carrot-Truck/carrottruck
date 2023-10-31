import styled, { css } from 'styled-components';
import { ButtonColorStyles, ButtonRadiusStyles, ButtonSizeStyles } from '../../../styles/style';

interface IButtonWrapperProps {
  $size: 's' | 'm' | 'l' | 'full';
  $radius: 's' | 'm' | 'l';
  $color: 'Normal' | 'Primary' | 'Danger' | 'Success' | 'SubFirst' | 'SubSecond';
  disabled: boolean;
}

export const ButtonWrapper = styled.button<IButtonWrapperProps>`
  border-radius: var(--radius-m);
  height: 48px;

  ${({ $size }) => ButtonSizeStyles[$size]}
  ${({ $radius }) => ButtonRadiusStyles[$radius]}
	${({ $color }) => ButtonColorStyles[$color]}
	${({ disabled }) =>
    disabled
      ? css`
          background-color: var(--gray-300);
          color: var(--gray-500);
        `
      : css``}
`;
