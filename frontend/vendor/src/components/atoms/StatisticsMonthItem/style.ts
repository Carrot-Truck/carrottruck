import styled from "styled-components";

export const StatisticsMonthItemWrapper = styled.div`
  width: 100%;
  border-bottom: solid 1px var(--gray-200);
  padding-bottom: 5px;
  display: flex;
  font-color: var(--gray-600);

  .month-left-div {
    text-align: left;

    div {
      margin: 3px 0 3px 5px;
    }

    .month-date {
      font-weight: 700;
      margin-bottom: 20px;
    }

    .month-time {
      font-size: 0.8rem;
      margin-bottom: 5px;
    }
  }

  .month-right-div {
    margin: auto 10px auto auto;

    .next_arrow_wrapper {
      margin-bottom: 10px;
    }

    .month-total-sale {
      font-size: 1.1rem;
      font-weight: 700;
    }
  }
`;
