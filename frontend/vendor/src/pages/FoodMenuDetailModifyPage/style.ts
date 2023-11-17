import styled from "styled-components";

export const FoodMenuDetailModifyLayout = styled.div`
  width: 100vw;
  padding-bottom: 200px;
  font-family: BMJUA;
  min-height: 100vh;
  padding: 0 10px;

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

  .inputField {
    display: flex;
    flex-direction: column;
    padding: 10px 0px;
    justify-content: left;

    p {
      font-size: 1.25rem;
      font-weight: 700;
      text-align: left;
      padding-bottom: 5px;
    }

    .menuPicture {
      display: none;
    }

    .pictureWrapper {
      display: flex;
      flex-direction: column;

      justify-content: left;
      height: 3rem;
    }

    label {
      width: 100%;
      height: 3rem;
      padding: 0.5rem;
      padding-right: 1rem;
      border-radius: var(--radius-m);
      border: 1px solid var(--gray-100);
      background-color: var(--gray-100);

      &:focus-visible {
        border: none;
        outline: 2px solid var(--main-color);
      }
    }
  }
`;
