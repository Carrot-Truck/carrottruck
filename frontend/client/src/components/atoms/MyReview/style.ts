import styled from 'styled-components';

export const MyReviewItemWrapper = styled.div`
  padding: 15px 10px 5px 0px;
  text-align: left;
  p {
    font-size: 1rem;
    font-weight: 400;
    color: var(--black-400);
  }

  p: nth-of-type(1) {
    color: var(--gray-500);
    font-size: 0.75rem;
  }

  .name {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    p: nth-of-type(1) {
      color: var(--black-400);
      font-size: 1.25rem;
    }
  }

  .reviewImage {
    img {
      width: 320px;
      height: 240px;
    }
  }
`;
