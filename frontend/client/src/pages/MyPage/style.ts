import styled from 'styled-components';

export const MyPageLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  font-family: BM JUA_TTF;
  min-height: 100vh;

  .title {
    border-top: 1px solid var(--gray-400);
    border-bottom: 1px solid var(--gray-400);
    padding: 10px 0px;
    p {
      font-family: BM JUA_TTF;
      font-weight: 700;
      font-size: 1.5rem;
      text-align: left;
    }
  }
  
  .header {
    display: flex;
    flex-direction: row;
    position: relative;
    align-items: center; /* 수직 중앙 정렬 */
    justify-content: space-between; /* 요소들을 양 끝으로 정렬 */
  }

  .userInfo {
    display: flex;
    flex-direction: row;
    padding: 0px 3rem;

    p {
      font-size: 1.5rem;
      font-weight: 700;
      padding-bottom: 10px;
      border-bottom: 3px solid var(--main-color);
    }

    p: nth-child(1) {
      color: var(--main-color);
    }

    
    
  }

  .history {
    display: flex;
    flex-direction: row;
    margin: 10px;
    padding: 10px 15px;

    div {
      width: 100px;
      height: 100px;
      background-color: var(--gray-100);
      border-radius: var(--radius-m);
      display: flex;
      flex-direction: column;
      flex-wrap: wrap;
      justify-content: center;
      margin: 10px;
    }

    img {
      height: 50px;
    }

    p {
      padding-top: 10px;
      font-size: 1rem;
      font-weight: 1000;
    }
  }
  
`;

export const LogoutP = styled.p`
  font-size: 1rem;
  position: absolute;
  right: 0;
  top: 50%; /* 부모 요소의 50% 위치에 */
  // transform: translateY(-50%); /* 자신의 높이의 절반만큼 올려서 중앙에 위치 */
`;