import styled from 'styled-components';

export const FoodTruckMenuItemWrapper = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  margin: 0.5rem;
  text-align: center;

  img {
    width: 30%;
    height: 3rem;
  }
`;

export const MenuTextWrapper = styled.div`
  display: flex:
  flex-direction:column:
  width: 100%;
  justify-content: flex-start;
  text-align: left;

  p: nth-child(1) {
    font-size: 2rem;
    font-color: var(--black-300);
    font-weight: 900;
    width: 100%;
  }
  p: nth-child(2) {
    font-size: 1.5rem;
    font-color: var(--black-300);
    font-weight: 700;
    width: 100%;
  }
  p: nth-child(3) {
    font-size: 1rem;
    font-color: var(--black-300);
    font-weight: 500;
    width: 100%;
  }
`;
