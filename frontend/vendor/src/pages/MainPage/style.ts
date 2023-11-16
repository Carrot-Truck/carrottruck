import styled from "styled-components";

export const MainPageLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
  max-height: 100vh;
  min-height: 100vh;
  justify-content: center;
`;

export const LogoutP = styled.p`
  font-size: 1rem;
  position: absolute;
  right: 0;
  top: 50%; /* 부모 요소의 50% 위치에 */
  // transform: translateY(-50%); /* 자신의 높이의 절반만큼 올려서 중앙에 위치 */
`;
