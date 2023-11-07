import styled from 'styled-components';

export const BusinessRegistrationContainer = styled.div`
  position: relative;
  height: calc(100vh - 76px);
  padding-top: 1rem;

  .registration-form {
    display: flex;
    flex-direction: column;
    gap: 2rem;
    text-align: left;

    padding: 1rem;
    // border: 1px solid var(--gray-300);
    // border-radius: var(--radius-s);

    .field {
      display: flex;
      flex-direction: column;
      gap: 10px;
    }

    .input {
      display: flex;
      flex-direction: column;
      gap: 5px;
    }

    .field-row {
      display: flex;
      flex-direction: row;

      .input {
        flex-grow: 1;
      }
    }
  }
  .next-btn {
    button {
      position: absolute;
      bottom: 2rem;
    }
  }
`;
