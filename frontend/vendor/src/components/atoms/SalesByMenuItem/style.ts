import styled from "styled-components";

export const SalesByMenuItemWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 5px 0px 5px 2px;

  .menu-item-menu-name {
    color: var(--gray-600);
    width: 60vw;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: left;
  }

  .menu-item-menu-orders {
    width: 15vw;
    text-align: right;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .menu-item-menu-sales {
    width: 25vw;
    text-align: right;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;
