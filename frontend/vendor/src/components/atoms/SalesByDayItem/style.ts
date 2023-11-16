import styled from "styled-components";

export const SalesByDayItemWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 5px 10px 5px 10px;

  .day-item-day-name {
    width: 50vw;
    text-align: left;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .day-item-day-orders {
    width: 20vw;
    text-align: right;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .day-item-day-sales {
    width: 30vw;
    text-align: right;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;
