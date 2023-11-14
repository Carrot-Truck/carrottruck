import styled from "styled-components";

export const ShoppingCartContainer = styled.div`
  position: absolute; /* 화면에 고정 */
  right: 10px; /* 오른쪽에서 10px 떨어진 위치 */
  top: 10px; /* 위에서 10px 떨어진 위치 */
  cursor: pointer; /* 마우스 오버 시 포인터로 변경 */
  z-index: 1000; /* 다른 요소들보다 위에 표시되도록 z-index 설정 */

  /* 마우스 오버 시 스타일 */
  &:hover {
    opacity: 0.7; /* 투명도 조정 */
  }

  /* 반응형 디자인을 위한 최대 너비 설정 */
  @media (max-width: 360px) {
    right: 0; /* 화면 가로 길이가 360px 이하일 때 오른쪽에 바짝 붙임 */
    margin-right: 10px; /* 오른쪽 가장자리와 10px 거리 유지 */
  }

  /* 아이콘 크기는 필요에 따라 조정 */
  img {
    width: 24px; /* 아이콘의 너비 */
    height: 24px; /* 아이콘의 높이 */
  }
`;