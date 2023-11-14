import styled from 'styled-components';

export const FoodMenuModifyLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  font-family: BMJUA;
  min-height: 100vh;      // 최소 높이를 뷰포트 높이로 설정

  .header {
    width: 70%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    p {
      font-size: 2rem;
      justify-content: right;
      font-weight: 700;
    }
  }

  .plusButton {
    float: right;
    padding: 5px;
  }
`;
