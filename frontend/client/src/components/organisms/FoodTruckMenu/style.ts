import styled from 'styled-components';

export const FoodTruckMenuContainer = styled.div`
  position: relative;
  width: 100%;
  // border-bottom: 1px solid var(--gray-400);
    background-color: #fff; // 사진에 기반한 배경색
  // border: 1px solid #ccc; // 경계선 스타일
  // padding: 16px; // 내부 여백
  // margin-bottom: 16px; // 하단 여백
  // margin-right: 2px;
  font-family: '나눔고딕', 'Nanum Gothic', sans-serif; // 폰트 스타일

  h3 {
    font-size: 1.2rem; // 제목 폰트 크기
    color: #000; // 제목 폰트 색상
    margin-bottom: 8px; // 제목과 내용 사이의 여백
  }

  p {
    font-size: 1rem; // 내용 폰트 크기
    color: #333; // 내용 폰트 색상
    line-height: 1.5; // 라인 높이
    margin-bottom: 4px; // 내용 간 여백
  }
`;
