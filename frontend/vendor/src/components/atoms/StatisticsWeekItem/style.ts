import styled from "styled-components";

export const StatisticsWeekItemWrapper = styled.div`
  width: 100%;
  border-bottom: solid 1px var(--gray-200);
  padding-bottom: 5px;
  display: flex;
  font-color: var(--gray-600);

  .week-left-div {
    text-align: left;

    div {
      margin: 3px 0 3px 5px;
    }

    .week-date {
      font-weight: 700;
      margin-bottom: 20px;
    }

    .week-time {
      font-size: 0.8rem;
      margin-bottom: 5px;
    }
  }

  .week-right-div {
    margin: auto 10px auto auto;

    .next_arrow_wrapper {
      margin-bottom: 10px;
    }

    .week-total-sale {
      font-size: 1.1rem;
      font-weight: 700;
    }
  }
`;
