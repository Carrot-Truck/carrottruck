import styled, { css } from "styled-components";
import { ButtonRadiusStyles, ButtonSizeStyles } from "../../../styles/style";

interface IButtonOutlineWrapperProps {
  $size: "s" | "m" | "l" | "full";
  $radius: "s" | "m" | "l";
  $color: "Normal" | "Primary" | "Danger" | "Success" | "SubFirst" | "SubSecond";
  disabled: boolean;
}

export const ButtonOutlineWrapper = styled.button<IButtonOutlineWrapperProps>`
  border-radius: var(--radius-m);
  height: 48px;
  font-family: BMJUA;
  font-size: 1.25rem;
  font-weight: 900;

  ${({ $size }) => ButtonSizeStyles[$size]}
  ${({ $radius }) => ButtonRadiusStyles[$radius]}
	${({ $color }) => ButtonColorStyles[$color]}
	${({ disabled }) =>
    disabled
      ? css`
          border-color: var(--gray-300);
          color: var(--gray-100);
        `
      : css``}
`;
