import styled, { css } from "styled-components";

interface MapWrapperProps {
  dynamicHeight?: string;
  translateY?: string;
}

export const MapWrapper = styled.div<MapWrapperProps>`
  width: 100%;
  ${(props) =>
    props.dynamicHeight !== (null || undefined)
      ? css`
          height: ${props.dynamicHeight};
        `
      : css`
          height: 600px;
        `}
  position: absolute;
  ${(props) =>
    props.translateY !== (null || undefined)
      ? css`
        transform: translateY(${props.translateY};
      `
      : css``}
`;
