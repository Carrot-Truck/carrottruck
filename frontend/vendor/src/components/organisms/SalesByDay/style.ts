import styled from "styled-components";

export const SalesByDayContainer = styled.div`
  padding-bottom: 10px;

  .day-criteria {
    display: flex;
    justify-content: right;
    margin-right: 10px;

    div {
      margin: 0 7px;
      padding-bottom: 7px;
    }

    .selected {
      font-weight: bold;
    }
  }

  .day-doughnut-wrapper {
    width: 80%;
    height: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto 15px auto;
  }
`;
