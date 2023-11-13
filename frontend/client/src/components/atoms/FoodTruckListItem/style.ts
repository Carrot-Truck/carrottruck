import styled from 'styled-components';

export const FoodTruckListItemWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2px;
  align-self: stretch;
  padding: 10px 0px 5px 0px;

  .restaurant {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: spacebetween;
    align-items: center;
    gap: 73px;
    align-self: stretch;

    img {
      width: 45px;
      height: 45px;
    }

    .category {
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
            font-size: 1rem;
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
    flex-direction: row;
    align-items: flex-start;
    text-align: left;
    gap: 16px;

    span {
      color: var(--black-400);
    }
  }

  .foodTruckImage {
    width: 386px;
    height: 125px;
    background-color: var(--gray-100);
  }
`;
