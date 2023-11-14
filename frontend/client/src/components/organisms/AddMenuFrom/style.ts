import styled from 'styled-components';

export const AddMenuLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  min-height: 100vh; // 최소 높이를 뷰포트 높이로 설정
  justify-content: space-between; // 내용과 NavBar 사이의 공간을 균등하게 분배

  .content {
    flex-grow: 1; // 내용이 차지할 수 있는 만큼의 공간을 차지하도록 설정
    // 필요한 경우 여기에 추가 스타일을 적용합니다.
  }

  .header {
    display: flex;
    flex-direction: rows;
    justify-content: space-between;

    p {
      font-family: BM JUA_TTF;
      font-size: 2rem;
      justify-content: center;
    }

    span {
      font-family: BM JUA_TTF;
      font-size: 2rem;
      text-align: center;
    }
  }

  .headerImage {
    width: 100%;
    height: 20vh;
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
      margin-bottom: 10px;
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

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
    background: #fff; // 이 부분은 예시이며, 디자인에 맞게 조정해야 합니다.
  }

  .headerImage {
    width: 100%;
    height: auto; // 이미지의 비율에 따라 자동으로 높이 조정
    object-fit: cover; // 이미지 비율을 유지하면서 빈 공간 없이 컨테이너를 채움
  }

  .switchButton {
    display: flex;
    width: 100%;
    padding: 5px 10px 5px 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    border-radius: var(--radius-m);
    background: var(--sub-color-1);
  }
`;
