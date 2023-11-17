import styled from "styled-components";

export const FoodMenuDetailLayout = styled.div`
  width: 100vw;
  padding-bottom: 200px;
  font-family: BMJUA;

  .header {
    width: 70%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    p {
      font-size: 2rem;
      justify-content: right;
      font-weight: 700;
    }
  }

  .menuInfo {
    width: 100%;
    text-align: left;
    border-bottom: 1px solid var(--gray-300);
    padding: 15px 0px;

    p: nth-child(1) {
      color: var(--black-400);
      font-size: 1.5rem;
      font-weight: 700;
      padding-bottom: 5px;
    }
    p: nth-child(2) {
      font-size: 1rem;
      font-weight: 400;
    }
  }

  .priceInfo {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    border-bottom: 1px solid var(--gray-300);
    font-size: 1.5rem;
    font-weight: 700;
    padding: 15px 0px;
  }
`;
