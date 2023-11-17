import styled from "styled-components";

export const OrderHistoryItemWrapper = styled.div`
  width: 100%;
  border-bottom: 1px solid black;
  padding: 10px;

  .order-history-item {
    display: flex;
    justify-content: space-between;

    .left-column {
      flex-grow: 1;
      max-width: 60%;

      div {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

        margin-bottom: 3px;
        color: gray;
        text-align: left;
      }
    }

    .right-column {
      display: flex;
      margin-top: 3px;
      margin-left: auto;
      margin-right: 5vw;
      align-items: center;
      flex-direction: column;
      max-width: 40%;

      div {
        text-align: center;
        margin-bottom: 3px;

        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .arrow-wrapper {
        display: flex;
        width: 36px;
        height: 36px;
        padding: 6px;
        justify-content: center;
        align-items: center;
        flex-shrink: 0;
      }
    }
  }

  .order-history-detail {
    display: flex;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    padding: 10px;
    margin-bottom: 10px;

    .client-info {
      margin-bottom: 10px;

      .client-info-detail {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;

        .text-box-left {
          font-weight: 600;
          div {
            margin-bottom: 5px;
          }
        }

        .text-box-right {
          div {
            text-align: right;
            margin-bottom: 5px;
            margin-right: 3px;
          }
        }
      }

      .order-count {
        color: var(--gray-600);
      }
    }

    .order-info {
      .menu-info-items {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;
      }

      .total-price {
        font-weight: 700;
        display: flex;
        justify-content: space-between;
        margin: 15px 0 5px 0;

        div {
          font-size: 1.1rem;
        }
      }
    }
  }

  .middle-title {
    text-align: left;
    font-size: 1.1rem;
    font-weight: 700;
    padding: 3px 5px 5px 5px;
    border-bottom: 1px solid gray;
    margin-bottom: 10px;
  }

  .button-wrapper {
    margin: 5px;

    button {
      margin: 5px;
    }
  }
`;
