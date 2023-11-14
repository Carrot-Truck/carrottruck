import styled, { css } from "styled-components";

interface IMenuSelectorContainer {
  $onSale: boolean;
}

export const MenuSelectorContainer = styled.div<IMenuSelectorContainer>`
  width: 100%;
  min-height: 30vw;
  max-height: 70vw;
  overflow: auto;

  ${({ $onSale }) =>
    $onSale
      ? css`
          margin-top: 30vw;
        `
      : css``}
`;
