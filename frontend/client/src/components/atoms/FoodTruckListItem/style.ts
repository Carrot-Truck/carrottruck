import styled from 'styled-components';

export const FoodTruckListItemWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
  align-self: stretch;
  padding: 10px 0px 5px 0px;
  font-family: BM JUA_TTF;

  .restaurant {
    width: 360px;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    gap: 73px;
    align-self: stretch;

    img {
      width: 45px;
      height: 45px;
      margin: 0px 10px;
    }

    .category {
      width: 100%;
      display: flex;
      flex-direction: row;

      .name {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        color: var(--black-400);
        text-align: left;
        padding: 0px 0px 10px 0px;

        p {
          font-size: 1.25rem;
        }

        .location {
          display: flex;
          flex-direction: row;
          color: var(--gray-200);

          p {
            font-size: 0.75rem;
          }

          img {
            width: 15px;
            height: 15px;
          }
        }
      }
    }
  }

  .reviews {
    display: flex;
    width: 350px;
    flex-direction: row;
    align-items: stretch;
    text-align: left;
    gap: 16px;

    span {
      color: var(--black-400);
    }
  }

  .foodTruckImage {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 340px;
    height: 125px;
    padding: 5px 10px;
    background-color: var(--gray-100);

    img {
      width: 340px;
      height: 125px;
    }
  }
`;
