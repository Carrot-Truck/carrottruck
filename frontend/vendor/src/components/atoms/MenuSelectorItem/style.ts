import styled from "styled-components";

export const MenuSelectorItemWrapper = styled.div`
  width: 100%;
  height: 20vw;
  position: relative;
  border-bottom: 1px solid black;

  .menu-item-image {
    display: inline-block;
    position: absolute;
    width: 20%;
    height: 100%;
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
