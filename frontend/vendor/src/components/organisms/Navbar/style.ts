import styled from "styled-components";

export const NavbarContainer = styled.div`
  display: flex;
  width: 360px;
  padding: 16px 28px 24px 28px;
  justify-content: space-between;
  align-items: center;
  background: var(--gray-100);
  position: fixed; // 고정 위치
  bottom: 0; // 화면 하단에 위치
  z-index: 2000;
`;
