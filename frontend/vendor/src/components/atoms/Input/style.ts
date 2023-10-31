import styled from 'styled-components';

interface IInputWrapperProps {
  $isIcon: boolean;
}
export const InputWrapper = styled.div<IInputWrapperProps>`
  position: relative;

  input {
    width: 100%;
    height: 3rem;
    padding: 0 ${({ $isIcon }) => ($isIcon ? '2.5rem' : '1rem')};
    padding-right: 1rem;
    border-radius: var(--radius-m);
    border: 1px solid var(--gray-300);

    &:focus-visible {
      border: none;
      outline: 2px solid var(--main-color);
    }
  }

  .icon {
    position: absolute;
    top: 50%;
    left: 0.75rem;
    transform: translateY(-50%);

    svg {
      fill: var(--gray-400);
      width: 1.5rem;
      height: 1.5rem;
    }
  }
`;
