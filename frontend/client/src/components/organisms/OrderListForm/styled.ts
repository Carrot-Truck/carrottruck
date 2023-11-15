import styled from 'styled-components';

export const OrderListFormContainer = styled.div`
  .waiting {
    font-size: 0.75rem;
    text-align: left;
    min-height: 100vh;

    .orderDetail {
      border: 1px solid var(--black-300);
      border-radius: var(--radius-m);
      margin: 5px 3px 10px 3px;
      padding: 8px;
      font-size: 1.25rem;

      .orderDetailMenu {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        font-family: NanumSquareNeo;
        padding: 5px 5px;
      }

      .totalPrice {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        border-top: 1px solid var(--black-100);
        padding: 10px 0px;
        font-size: 1.5rem;
      }
    }
  }

  .title {
    border-top: 1px solid var(--gray-400);
    border-bottom: 1px solid var(--gray-400);
    padding: 10px 0px;
    p {
      font-family: BM JUA_TTF;
      font-weight: 700;
      font-size: 1.5rem;
      text-align: left;
    }
  }
  .nowTitle {
    font-family: BM JUA_TTF;
    font-size: 1.5rem;
  }

  .gray {
    color: var(--gray-300);
  }
`;
