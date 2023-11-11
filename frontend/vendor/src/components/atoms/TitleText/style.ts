import styled from "styled-components";

interface TitleTextWrapperProps {
  $size: "s" | "m" | "l";
}

export const TitleTextWrapper = styled.div<TitleTextWrapperProps>`
  width: 100%;
  font-family: BMJUA;
  font-weight: 700;
  ${({ $size }) => TextSizeStyles[$size]}
  text-align: left;
`;

const TextSizeStyles = {
  s: `
  font-size: 1.2rem;
  margin-bottom: 5px;
  margin-left: 5px;
  `,
  m: `
  font-size: 1.5rem;
  margin-bottom: 10px;
  margin-left: 10px;
  `,
  l: `
  font-size: 2rem;
  margin-bottom: 20px;
  margin-left: 20px;
  `,
};
