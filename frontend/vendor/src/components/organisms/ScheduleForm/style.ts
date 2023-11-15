import styled from 'styled-components';

export const LoginFormContainer = styled.div`
  // border: 1px solid var(--gray-300);
  // border-radius: var(--radius-s);
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;

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
  flex-direction: row;
  gap: 5px;
`;


export const Input = styled.input`
  flex: 2; /* 4분의 1을 차지하도록 flex 비율 설정 */
  width: 25%;
  disabled: true;
`;


export const P = styled.p`
  width: 5%; /* 8분의 1을 차지하도록 flex 비율 설정 */
  display: flex;
  align-items: center;
  text-align: center;
  height: 40px; // 예를 들어, FieldSet 컴포넌트의 높이에 맞추기 위한 높이 설정
  margin: 0; // 기본 마진 제거
`;

