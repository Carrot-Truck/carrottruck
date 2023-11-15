import styled from 'styled-components';

export const AddMenuLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  min-height: 100vh;      // 최소 높이를 뷰포트 높이로 설정
  justify-content: space-between; // 내용과 NavBar 사이의 공간을 균등하게 분배

  .header {
    margin-top: 1rem;
    margin-bottom: 2rem;
    display: flex;
    flex-direction: rows;

    p {
      font-family: BMJUA;
      font-size: 2rem;
      justify-content: center;
    }
  }

  .time{
    display: flex;
    position: relative;
    flex-direction: rows;
    margin-bottom: 2rem;
  }

  .storeInfo {
    padding: 20px;
    background: white; // 이 부분은 예시이며, 디자인에 맞게 조정해야 합니다.
    font-family: 'BM JUA_TTF'; // 폰트는 디자인에 맞게 조정
    font: black;
    
    .foodTruckName {
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      span {
        color: var(--main-color);
        text-align: left;
        font-size: 2.5rem;
        font-style: normal;
        font-weight: 400;
      }
    }

    .location {
      margin-bottom: 2rem;
      text-align: left;
      font-size: 1rem; // 메뉴 설명의 크기를 조정
      color: black; // 메뉴 설명의 색상을 조정
    }

    .quantity {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 20px;
      color: black;
      
      button {
        font-size: 1.5rem; // 버튼의 크기를 조정
        width: 30px; // 버튼의 너비를 조정
        height: 30px; // 버튼의 높이를 조정
        line-height: 30px; // 버튼 내 텍스트의 라인 높이를 조정
        border: 1px solid #ccc; // 버튼의 테두리를 조정
        background: #fff; // 버튼의 배경색을 조정
        color: #333; // 버튼의 텍스트 색상을 조정
      }
      
      span {
        font-size: 1.5rem; // 수량 숫자의 크기를 조정
      }
    }

    .totalPrice {
      font-size: 1.5rem; // 총 금액의 크기를 조정
      color: #333; // 총 금액의 색상을 조정
      margin-bottom: 30px;
    }
  }
  
`;

export const Input = styled.input`
  flex: 2; /* 4분의 1을 차지하도록 flex 비율 설정 */
  width: 25%;
`;


export const P = styled.p`
  width: 5%; /* 8분의 1을 차지하도록 flex 비율 설정 */
  display: flex;
  align-items: center;
  text-align: center;
  height: 40px; // 예를 들어, FieldSet 컴포넌트의 높이에 맞추기 위한 높이 설정
  margin: 0; // 기본 마진 제거
`;