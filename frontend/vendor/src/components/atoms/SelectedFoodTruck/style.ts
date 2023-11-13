import styled from "styled-components";

export const SelectedFoodTruckWrapper = styled.div`
  display: flex;
  width: 60%;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  margin-left: auto;

  span {
    font-size: 1.2rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    padding-left: 10px;
    padding-right: 10px;
  }
`;
