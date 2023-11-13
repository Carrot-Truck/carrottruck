import styled, { css } from "styled-components";

interface FoodTruckListItemWrapperProps {
  selected: boolean;
}

export const FoodTruckListItemWrapper = styled.div<FoodTruckListItemWrapperProps>`
  width: 100%;
  display: inline-block;
  border: solid 1px black;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: white;

  ${({ selected }) =>
    selected
      ? css`
          font-weight: 700;
        `
      : css``}
`;
