import styled from "styled-components";

export const SurveyListItemWrapper = styled.div`
  width: 100%;

  display: flex;
  justify-content: space-between;
  padding: 3px 10px;
  margin-right: 10px;
  border-bottom: 1px solid gray;

  .survey-image-wrapper {
    width: 48px;
    height: 48px;
    border-radius: 50px;

    img {
      width: 100%;
      height: 100%;
      overflow: cover;
    }
  }

  .survey-text {
    width: 30%;
    margin: auto auto auto 20px;
    font-weight: 600;
  }

  :hover {
    cursor: pointer;
    background-color: var(--gray-200);
  }
`;
