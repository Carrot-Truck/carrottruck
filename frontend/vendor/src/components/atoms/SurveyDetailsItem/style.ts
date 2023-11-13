import styled from "styled-components";

export const SurveyDetailsItemWrapper = styled.div`
  width: 100%;
  border-bottom: solid 1px black;
  margin-bottom: 10px;

  .detail-item {
    position: relative;
    margin: 5px;

    .detail-first-row {
      display: inline-block;

      .detail-nickname {
        position: absolute;
        margin-left: 5px;
        top: 0;
        left: 0;
      }

      .detail-date {
        position: absolute;
        top: 0;
        right: 0;
      }
    }

    .detail-content {
      margin-top: 5px;
      font-size: 1.2rem;
      text-align: left;
    }
  }
`;
