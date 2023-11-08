import styled from 'styled-components';

export const ReigsterFoodTrcukFormContainer = styled.div`
  display: flex;
  flex-direction: column; /* 버튼을 수직으로 정렬하기 위해 열 방향으로 설정합니다. */
  justify-content: center;
  align-items: center;
  min-height: 90vh;
`;

export const CenteredButton = styled.button`
  font-size: 1.5rem; /* 원하는 크기로 설정하세요. */
  padding: 3rem; /* 원하는 패딩 값으로 설정하세요. */
  text-align: center;
  color: white; /* 텍스트 색상을 원하는 색상으로 설정하세요. */
  border: none;
  border-radius: 5px; /* 원하는 모양으로 설정하세요. */
  cursor: pointer;
  height: 30rem;
  margin-bottom: 5rem;

  /* 버튼을 수직 및 수평 가운데 정렬합니다. */
  display: flex;
  justify-content: center;
  align-items: center;
`;