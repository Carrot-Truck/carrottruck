import styled from 'styled-components';

export const FoodTruckModifyContainer = styled.div`
  position: relative;
  height: calc(100vh - 76px);
  padding-top: 1rem;
  text-align: left;

  .regist-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;

    padding: 1rem;
    // border: 1px solid var(--gray-300);
    // border-radius: var(--radius-s);

    .input {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }

    .field {
      display: flex;
      flex-direction: column;
      gap: 10px;
    }
    .truckPicture {
      display: none;
    }

    label {
      width: 100%;
      height: 3rem;
      padding: 1rem;
      padding-right: 1rem;
      border-radius: var(--radius-m);
      border: 1px solid var(--gray-100);
      background-color: var(--gray-100);

      &:focus-visible {
        border: none;
        outline: 2px solid var(--main-color);
      }
    }

    select {
      width: 100%;
      height: 3rem;
      padding: 1rem;
      padding-right: 1rem;
      border-radius: var(--radius-m);
      border: 1px solid var(--gray-100);
      background-color: var(--gray-100);

      &:focus-visible {
        border: none;
        outline: 2px solid var(--main-color);
      }
    }

    .field-row {
      display: flex;
      flex-direction: row;
    }
  }
  .next-btn {
    button {
      position: absolute;
      bottom: 2rem;
    }
  }
`;
