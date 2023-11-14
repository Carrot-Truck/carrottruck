import styled from 'styled-components';

export const InfoItemContainer = styled.div`
display: grid;
grid-template-columns: 1fr 2fr; // 1:2의 비율로 두 열을 정의
align-items: center;
border: 1px solid #ccc;
padding: 8px;
margin-bottom: 8px;
color: black; // 글씨 색상을 검정색으로 변경

h3 {
  font-weight: bold;
  grid-column: 1 / -1; // h3는 전체 너비를 차지하도록 설정
  font-size: 1.2rem;
  margin-bottom: 8px; // 제목과 내용 사이의 여백
}

.label {
  text-align: right;
  padding-right: 8px;
  border-right: 1px solid #ccc;
  margin-bottom: 100px; // 제목과 내용 사이의 여백
}

.value {
  padding-left: 8px;
  margin-bottom: 10px; // 제목과 내용 사이의 여백
}

.full {
    margin-top: 10px;
    grid-column: 1 / -1;
    text-align: left;
}

.nickname{
  font-weight: bold;
}

`;