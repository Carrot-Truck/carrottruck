import styled, { css } from "styled-components";

interface MapWrapperProps {
  $dynamicheight?: string;
  $translateY?: string;
}

export const MapWrapper = styled.div<MapWrapperProps>`
  width: 100%;
  ${(props) =>
    props.$dynamicheight !== (null || undefined)
      ? css`
          height: ${props.$dynamicheight};
        `
      : css`
          height: 600px;
        `}
  position: absolute;
  ${(props) =>
    props.$translateY !== (null || undefined)
      ? css`
        transform: translateY(${props.$translateY};
      `
      : css``}
`;
