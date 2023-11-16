import styled from 'styled-components';
export const CartMenuItemWrapper = styled.div`
  display: flex;
  align-items: center; /* 이미지와 텍스트를 수직 중앙에 정렬 */
  flex-direction: row;
  width: 100%;
  margin: 0.5rem;
  text-align: center;
  border-top: 1px solid var(--gray-400);
  padding-top: 8px;

  img {
    width: 30%;
    height: 3rem;
    object-fit: cover; /* 이미지 비율 유지 */
  }

  button {
    background-color: var(--primary-color); /* 버튼 배경색 */
    color: red; /* 버튼 텍스트 색상 */
    // padding: 0.5rem 1rem; /* 버튼 패딩 */
    margin: 1rem;
    // border: 2px solid red; /* 회색 테두리 추가 */
    border-radius: 0.5rem; /* 둥근 모서리 */
    cursor: pointer; /* 마우스 오버시 커서 변경 */
    font-size: 1rem; /* 폰트 크기 */
  }
`;

export const MenuTextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 70%; /* 이미지 영역을 제외한 나머지 너비 */
  justify-content: flex-start;
  text-align: left;

  /* 버튼을 포함하는 div 스타일링 */
  div {
    display: flex;
    align-items: center;
    justify-content: space-around; /* 버튼 간격 조정 */
  }

  button {
    background-color: var(--primary-color); /* 버튼 배경색 */
    color: black; /* 버튼 텍스트 색상 */
    // padding: 0.5rem 1rem; /* 버튼 패딩 */
    margin: 1rem;
    border: 2px solid lightgray; /* 회색 테두리 추가 */
    border-radius: 0.5rem; /* 둥근 모서리 */
    cursor: pointer; /* 마우스 오버시 커서 변경 */
    font-size: 1rem; /* 폰트 크기 */
  }

  p:nth-child(1) {
    font-size: 1.25rem;
    color: var(--black-300); /* font-color가 아닌 color 사용 */
    font-weight: 900;
    marjin: 10px 0px;
  }
  p:nth-child(2) {
    font-size: 1rem;
    color: var(--black-300);
    font-weight: 700;
  }
  p:nth-child(3) {
    font-size: 1rem;
    color: var(--black-300);
    font-weight: 400;
  }
`;
