import styled from 'styled-components';
import { BOTTOM_SHEET_HEIGHT } from './BottomSheetOption';

export const MainPageLayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
`;

// FoodTruckList 컴포넌트 스타일
export const FoodTruckListLayout = styled.div`
  display: flex;
  flex-direction: column;
  width: 360px;
  align-items: center;
  position: fixed;
  z-index: 1000;
  top: calc(100% - 220px); /*시트가 얼마나 높이 위치할지*/
  left: 30;
  right: 30;

  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.6);
  height: ${BOTTOM_SHEET_HEIGHT}px;

  background: var(--white-color);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  transition: transform 500ms ease-out; /*바텀시트 애니메이션 속도*/

  .header {
    height: 24px;
    border-top-left-radius: 12px;
    border-bottom-right-radius: 12px;
    position: relative;
    padding-top: 12px;
    padding-bottom: 4px;

    .handle {
      width: 40px;
      height: 4px;
      border-radius: 2px;
      background-color: #dee2e6;
      margin: auto;
    }
  }
`;
