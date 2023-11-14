import styled from 'styled-components';

export const MyPageLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  font-family: BMJUA;

  .userInfo {
    display: flex;
    flex-direction: row;
    padding: 0px 3rem;

    p {
      font-size: 1.5rem;
      font-weight: 700;
      padding-bottom: 10px;
      border-bottom: 3px solid var(--main-color);
    }

    p: nth-child(1) {
      color: var(--main-color);
    }
  }

  .history {
    display: flex;
    flex-direction: row;
    margin: 10px;
    padding: 10px 15px;

    div {
      width: 100px;
      height: 100px;
      background-color: var(--gray-100);
      border-radius: var(--radius-m);
      display: flex;
      flex-direction: column;
      flex-wrap: wrap;
      justify-content: center;
      margin: 10px;
    }

    img {
      height: 50px;
    }

    p {
      padding-top: 10px;
      font-size: 1rem;
      font-weight: 1000;
    }
  }
`;
