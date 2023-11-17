import styled, { css } from "styled-components";

interface SwitchButtonContainerProps {
  $position?: string;
}

export const SwitchButtonContainer = styled.div<SwitchButtonContainerProps>`
  display: flex;
  width: 100%;
  padding: 10px 13px 10px 10px;
  justify-content: center;
  align-items: center;
  gap: 10px;
  border-radius: var(--radius-m);
  background: var(--sub-color-1);
  ${(props) =>
    props.$position !== (null || undefined)
      ? css`
          position: ${props.$position};
        `
      : css``}
`;
