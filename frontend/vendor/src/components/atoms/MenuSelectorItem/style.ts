import styled, { css } from "styled-components";

interface IMenuSelectorItemWrapperProps {
  $soldout: boolean;
}

export const MenuSelectorItemWrapper = styled.div<IMenuSelectorItemWrapperProps>`
  width: 100%;
  height: 20vw;
  position: relative;
  border-bottom: 1px solid black;

  ${({ $soldout }) =>
    $soldout
      ? css`
          background-color: var(--gray-300);
        `
      : css``}

  .menu-item-image {
    display: inline-block;
    position: absolute;
    width: 20vw;
    min-height: 20vw;
    max-height: 3rem;
    justify-content: center;
    align-items: center;
    flex-shrink: 0;
    left: 0;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .menu-item-detail {
    display: inline-block;
    position: absolute;
    text-align: left;
    left: 20vw;
    padding: 3px;
    margin-top: 0.1rem;
    height: 73%;

    .menu-name {
      font-weight: 700;
      padding-bottom: 0.3rem;
    }

    .menu-desc {
      font-size: 0.8rem;
      padding-bottom: 0.3rem;
      overflow: ellipse;
      color: gray;
    }
  }

  .menu-price {
    position: absolute;
    bottom: 0;
    text-align: left;
    left: 20vw;
    padding: 3px;
    margin-bottom: 0.3rem;
    height: 25%;
  }
`;
