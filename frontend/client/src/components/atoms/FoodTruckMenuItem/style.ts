import styled from 'styled-components';

export const FoodTruckMenuItemWrapper = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  margin: 0.5rem;
  text-align: center;
  border-top: 1px solid var(--gray-400);

  img {
    width: 30%;
    height: 3rem;
  }
`;

export const MenuTextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: flex-start;
  text-align: left;

  p:nth-child(1) {
    font-size: 1.25rem;
    color: var(--black-300);  // 'font-color' 대신 'color' 사용
    font-weight: 900;
    width: 100%;
  }
  p:nth-child(2) {
    font-size: 1rem;
    color: var(--black-300);  // 여기도 동일하게 수정
    font-weight: 700;
    width: 100%;
  }
  p:nth-child(3) {
    font-size: 0.75rem;
    color: var(--black-300);  // 여기도 동일하게 수정
    font-weight: 400;
    width: 100%;
  }
`;
