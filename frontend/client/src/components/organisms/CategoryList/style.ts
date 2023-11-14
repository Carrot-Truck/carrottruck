import styled from 'styled-components';


export const CategoryListContainer = styled.div`
display: grid;
  grid-template-columns: repeat(3, 1fr); // 4개의 열 생성
  grid-gap: 10px; // 격자 사이 간격
  justify-items: center; // 가로축 중앙 정렬
  align-items: center; // 세로축 중앙 정렬
  padding: 20px; // 전체적인 내부 여백
`;