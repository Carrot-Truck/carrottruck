import { styled } from 'styled-components';

export const CartOrderPageLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  color: black;
  font-family: BM JUA_TTF;
  text-align: left;

  .header {
    p {
      font-size: 1.5rem;
      justify-content: center;
      padding: 5px 0px 15px 25px;
    }
  }

  .payment {
    .info {
      p:nth-of-type(1) {
        font-size: 2rem;
        text-align: left;
        margin: 15px 30px;
        border-bottom: 2px solid var(--main-color);
      }
      p {
        font-size: 1.5rem;
        text-align: left;
        margin: 15px 30px;
        border-bottom: 2px solid var(--main-color);
      }
    }

    .price {
      font-family: NanumSquareNeo;
      font-size: 1rem;
      margin: 60% 30px 15px 30px;
      p:nth-of-type(1) {
        font-weight: 700;
      }
      div {
        display: flex;
        justify-content: space-between;
        border-bottom: 2px solid var(--main-color);
        padding: 15px 30px 15px 0px;
      }
    }
  }
`;
