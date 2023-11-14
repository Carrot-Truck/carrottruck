import styled from "styled-components";

interface TitleTextWrapperProps {
  $size: "s" | "m" | "l";
  $textAlign: "left" | "center" | "right";
}

export const TitleTextWrapper = styled.div<TitleTextWrapperProps>`
  width: 100%;
  ${({ $size }) => TextSizeStyles[$size]};
  ${({ $textAlign }) => TextAlign[$textAlign]};
  display: relative;

  div {
    display: absolute;
    font-family: BM JUA_TTF;
    font-weight: 700;
    top: 50%;
    transform: translateY(50%);
  }
`;

const TextSizeStyles = {
  s: `
  margin: 5px 0;
  div {
    font-size: 1.2rem
  };
  `,
  m: `
  margin: 10px 0;
  div {
    font-size: 1.5rem;
  }
    `,
  l: `
  margin: 20px 0;
  div {
    font-size: 2rem;
  };
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
