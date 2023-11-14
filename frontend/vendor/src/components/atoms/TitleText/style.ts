import styled from "styled-components";

interface TitleTextWrapperProps {
  $size: "s" | "m" | "l";
  $textAlign: "left" | "center" | "right";
}

export const TitleTextWrapper = styled.div<TitleTextWrapperProps>`
  width: 100%;
  font-family: BMJUA;
  font-weight: 700;
  ${({ $size }) => TextSizeStyles[$size]}
  ${({ $textAlign }) => TextAlign[$textAlign]};
`;

const TextSizeStyles = {
  s: `
  font-size: 1.2rem;
  margin: 5px 0;
  `,
  m: `
  font-size: 1.5rem;
  margin: 10px 0;
  `,
  l: `
  font-size: 2rem;
  margin: 20px 0;
  `,
};

const TextAlign = {
  left: `
  text-align: left;
  `,
  center: `
  text-align: center;
  `,
  right: `
  text-align: right;
  `,
};
