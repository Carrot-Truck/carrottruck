import styled from 'styled-components';

export const FoodTruckLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;

  .header {
    display: flex;
    flex-direction: rows;
    justify-content: space-between;

    p {
      font-family: BMJUA;
      font-size: 2rem;
      justify-content: center;
    }
  }

  .headerImage {
    width: 100%;
    height: 20vh;
  }

  .storeInfo {
    display: flex;
    flex-direction: column;
    font-family: BM JUA_TTF;

    .foodTruckName {
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      span {
        color: var(--main-color);
        text-align: left;
        font-size: 2.5rem;
        font-style: normal;
        font-weight: 400;
      }
    }

    img {
      width: 36px;
    }

    .location {
      width: 100%;
      display: flex;
      flex-direction: row;
      justify-content: left;

      span {
        color: var(--gray-300);
        font-size: 1.25rem;
        font-style: normal;
        font-weight: 400;
        line-height: normal;
        padding-right: 10px;
      }

      img {
        width: 1rem;
      }
    }

    .review {
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      img {
        width: 1rem;
      }

      #open {
        font-size: 1.25rem;
        color: var(--success-color);
      }

      #close {
        font-size: 1.25rem;
        color: var(--gray-200);
      }
    }
  }

  .switchButton {
    display: flex;
    width: 100%;
    padding: 5px 10px 5px 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    border-radius: var(--radius-m);
    background: var(--sub-color-1);
  }
`;
