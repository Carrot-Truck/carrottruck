import styled from 'styled-components';

export const MenuOptionWrapper = styled.div`
  width: 100%;
  text-align: left;
  font-family: BM JUA_TTF;
  padding: 10px 0px;
  border-bottom: 1px solid var(--gray-400);
  margin-bottom: 5px;

  .name {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    p {
      font-size: 1.5rem;
      font-weight: 700;
    }

    .checked {
      display: flex;
      flex-direction: row;
    }
  }
  p {
    font-size: 1rem;
  }
`;
