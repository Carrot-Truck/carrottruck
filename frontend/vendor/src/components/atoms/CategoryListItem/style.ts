import styled, { css } from "styled-components";

export const CategoryListItemWrapper = styled.div<{ selected: boolean | null }>`
  /* 기존 스타일 */
  cursor: pointer;
  transition: all 0.3s ease;

  /* 마우스 오버 상태의 스타일 */
  &:hover {
    background-color: #ffa126;
    color: white;
  }

  /* selected prop에 따라 스타일을 적용합니다. */
  ${({ selected }) =>
    selected &&
    css`
      background-color: #ffa126; /* 선택된 아이템의 배경색 */
      color: white; /* 선택된 아이템의 글자색 */
      border: 2px solid #ffa126; /* 선택된 아이템의 테두리 */
    `}
`;

export const CategoryTextWrapper = styled.div`
  font-size: 0.9rem; // 텍스트 크기 설정
  color: #333; // 텍스트 색상 설정
  text-align: center; // 텍스트 중앙 정렬
`;
