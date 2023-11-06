import styled from 'styled-components';

export const NavbarItemWrapper = styled.div`
  display: flex;
  height: 50px;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  svg {
    display: flex;
    width: 24px;
    height: 24px;
    justify-content: center;
    align-items: center;
    flex-shrink: 0;
  }

  span {
    color: var(--black-400);
    text-align: center;
    font-family: BM JUA_TTF;
    font-size: 0.75rem;
    font-style: normal;
    font-weight: 400;
    line-height: 23px; /* 191.667% */
    letter-spacing: -0.12px;
  }
`;
