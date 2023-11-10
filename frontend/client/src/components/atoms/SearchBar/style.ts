import styled from 'styled-components';
import { SearchBarColorStyles, SearchBarSizeStyles } from '../../../styles/style';

interface ISearchBarContainerProps {
  $size: 's' | 'l' | 'full';
  $color: 'Primary' | 'SubFirst' | 'SubSecond';
}

export const SearchBarContainer = styled.div<ISearchBarContainerProps>`
  position: relative;
  ${({ $color }) => SearchBarColorStyles[$color]};

  input {
    padding: 0 20px 0 60px;
    ${({ $size }) => SearchBarSizeStyles[$size]};
    border-radius: var(--radius-l);
    height: 40px;
  }
  .confirm-search-btn-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 45px;
    height: 100%;
    border-radius: var(--radius-l) 0 0 var(--radius-l);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    svg {
      fill: var(--white-color);
      width: 20px;
      height: 20px;

      &:hover {
        width: 25px;
        height: 25px;
      }
    }
  }
`;
