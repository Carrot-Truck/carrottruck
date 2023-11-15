import styled from "styled-components";

interface TitleTextWrapperProps {
  $size: "s" | "m" | "l";
  $textAlign: "left" | "center" | "right";
}

export const TitleTextWrapper = styled.div<TitleTextWrapperProps>`
  ${({ $size }) => TextSizeStyles[$size]};
  ${({ $textAlign }) => TextAlign[$textAlign]};
  width: 100%;
  font-family: BM JUA_TTF;
  font-weight: 700;
  margin: 0.8rem auto;
`;

const TextSizeStyles = {
  s: `
  font-size: 1.2rem
  `,
  m: `
  font-size: 1.5rem;
  `,
  l: `
  font-size: 2rem;
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
