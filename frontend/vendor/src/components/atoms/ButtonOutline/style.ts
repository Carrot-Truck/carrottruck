import styled, { css } from "styled-components";
import { ButtonOutlineColorStyles, ButtonRadiusStyles, ButtonSizeStyles } from "styles/style";

interface IButtonOutlineWrapperProps {
  $size: "s" | "m" | "l" | "full";
  $radius: "s" | "m" | "l";
  $color: "Normal" | "Primary" | "Danger" | "Success" | "SubFirst" | "SubSecond";
  disabled: boolean;
}

export const ButtonOutlineWrapper = styled.button<IButtonOutlineWrapperProps>`
  display: inline-block;
  border-radius: var(--radius-m);
  height: 48px;
  margin: 1em;
  white-space: nowrap;
  overflow: hidden;

  span {
    font-size: 1em;
    font-weight: 700;
  }

  ${({ $size }) => ButtonSizeStyles[$size]}
  ${({ $radius }) => ButtonRadiusStyles[$radius]}
	${({ $color }) => ButtonOutlineColorStyles[$color]}
	${({ disabled }) =>
    disabled
      ? css`
          border-color: var(--gray-300);
          color: var(--gray-100);
        `
      : css``}
`;
