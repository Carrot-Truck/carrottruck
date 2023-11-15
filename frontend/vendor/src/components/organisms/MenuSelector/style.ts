import styled from "styled-components";

interface IMenuSelectorContainer {
  $onSale: boolean;
}

export const MenuSelectorContainer = styled.div<IMenuSelectorContainer>`
  width: 100%;
  max-height: 85vw;
  overflow: auto;
  border-top: 1px solid gray;
  border-bottom: 3px solid gray;

  .sold-out {
    background-color: var(--gray-300);
  }
`;
