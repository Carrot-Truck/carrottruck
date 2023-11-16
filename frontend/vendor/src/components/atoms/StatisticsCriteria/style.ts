import styled, { css } from "styled-components";

interface IStatisticsCriteriaWrapperProps {
  selected: boolean;
}

export const StatisticsCriteriaWrapper = styled.p<IStatisticsCriteriaWrapperProps>`
  display: inline-block;
  border: 1px solid black;
  padding: 2px;
  width: 40px;
  position: sticky;
  top: 0;

  ${({ selected }) =>
    selected
      ? css`
          background-color: orange;
        `
      : css``}
`;
