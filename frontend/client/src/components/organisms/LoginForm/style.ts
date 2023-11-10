import styled from 'styled-components';

export const LoginFormContainer = styled.div`
  // border: 1px solid var(--gray-300);
  // border-radius: var(--radius-s);
  padding: 3rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  .login-message {
    color: var(--gray-500);
    font-size: 1.5rem;
    text-align: center;
  }

  .field-set {
    position: relative;
  }
`;

export const FieldSet = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;
