import styled from "styled-components";

export const MainPageLayout = styled.div`
  width: 100vw;
  padding-bottom: 200px;
  max-height: 100vh;
  min-height: 100vh;
  display: center;
  justify-content: center;
  font-family: BM JUA_TTF;
  min-height: 100vh;
  padding: 0 10px;

  .header {
    display: flex;
    flex-direction: row;
    position: relative;
    align-items: center; /* 수직 중앙 정렬 */
    justify-content: space-between; /* 요소들을 양 끝으로 정렬 */
  }
`;

export const LogoutP = styled.p`
  font-size: 1rem;
  position: absolute;
  right: 5%;
  top: 50%; /* 부모 요소의 50% 위치에 */
  // transform: translateY(-50%); /* 자신의 높이의 절반만큼 올려서 중앙에 위치 */
`;
