import styled from 'styled-components';

export const StoreInfoContainer = styled.div`
  .BusinessInfo {
    width: 360px;
    display: flex;
    flex-direction: row;
    border: 1px solid var(--gray-300);
    margin-top: 10px;

    .title {
      font-family: BMJUA;
      font-weight: 700;
      p: nth-child(1) {
        font-size: 1.5rem;
        margin: 15px;
      }

      p: nth-child(n + 2) {
        font-size: 1.25rem;
        margin: 15px;
      }
    }

    .content {
      font-weight: 500;

      p: nth-child(1) {
        font-size: 1rem;
        font-weight: 700;
        margin: 55px 15px 15px 5px;
      }

      .time {
        p {
          font-size: 1rem;
          font-weight: 500;
          margin: 5px;
        }
      }
    }
  }

  .SellerInfo {
    width: 360px;
    height: 174px;
    display: flex;
    flex-direction: column;
    border: 1px solid var(--gray-300);
    margin-top: 10px;

    .title {
      width: 100%;
      font-family: BMJUA;
      font-weight: 700;
      p {
        font-size: 1.5rem;
        text-align: left;
        padding: 15px;
      }
    }

    .content {
      display: flex;
      flex-direction: column;
      font-family: BMJUA;
      font-weight: 700;
      font-size: 1.25rem;

      div: nth-child(n) {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        padding: 10px 25px;

        p {
          text-align: left;
        }
      }
    }
  }

  .OriginInfo {
    width: 360px;
    display: flex;
    flex-direction: column;
    border: 1px solid var(--gray-300);
    margin-top: 10px;

    .title {
      font-family: BMJUA;
      font-weight: 700;
      p {
        font-size: 1.5rem;
        margin: 15px;
        text-align: left;
      }
    }

    .content {
      display: flex;
      flex-direction: column;
      font-weight: 700;
      padding: 5px 20px;
    }
  }
`;
