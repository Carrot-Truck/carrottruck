import { css } from 'styled-components';

// SearchBar
export const SearchBarSizeStyles = {
  s: css`
    width: 10rem;
  `,
  l: css`
    width: 20rem;
  `,
  full: css`
    width: 100%;
  `
};

// SearchBar
export const SearchBarColorStyles = {
  Primary: css`
    input {
      border: 1px solid var(--main-color);

      &:focus-visible {
        outline: 1.5px solid var(--main-color);
      }
    }

    .confirm-search-btn-wrapper {
      border: 1px solid var(--main-color);
      background-color: var(--main-color);
    }
  `,

  SubFirst: css`
    input {
      border: 1px solid var(--sub-color-1);

      &:focus-visible {
        outline: 1.5px solid var(--sub-color-1);
      }
    }

    .confirm-search-btn-wrapper {
      border: 1px solid var(--sub-color-1);
      background-color: var(--sub-color-1);
    }
  `,
  SubSecond: css`
    input {
      border: 1px solid var(--sub-color-2);

      &:focus-visible {
        outline: 1.5px solid var(--sub-color-2);
      }
    }

    .confirm-search-btn-wrapper {
      border: 1px solid var(--sub-color-2);
      background-color: var(--sub-color-2);
    }
  `
};

// Button
export const ButtonSizeStyles = {
  s: css`
    width: 90px;
    height: 40px;
  `,
  m: css`
    width: 150px;
    height: 48px;
  `,
  l: css`
    width: 210px;
    height: 48px;
  `,
  full: css`
    width: 100%;
    height: 48px;
  `,
  big: css`
    width: 80%;
    height: 210px;
  `
};

export const ButtonColorStyles = {
  Normal: css`
    background-color: var(--gray-100);
    color: var(--gray-500);
  `,
  Primary: css`
    background-color: var(--main-color);
    color: var(--white-color);
  `,
  SubFirst: css`
    background-color: var(--sub-color-1);
    color: var(--white-color);
  `,
  SubSecond: css`
    background-color: var(--sub-color-2);
    color: var(--white-color);
  `,
  Danger: css`
    background-color: var(--danger-color);
    color: var(--white-color);
  `,
  Success: css`
    background-color: var(--success-color);
    color: var(--white-color);
  `
};

export const ButtonRadiusStyles = {
  s: css`
    border-radius: var(--radius-s);
  `,
  m: css`
    border-radius: var(--radius-m);
  `,
  l: css`
    border-radius: var(--radius-l);
  `
};

// CheckTextButton
export const CheckTextButtonStyles = {
  s: css`
    svg {
      width: 24px;
      height: 24px;
    }
  `,
  m: css`
    span {
      font-size: 1.25rem;
    }
    svg {
      width: 28px;
      height: 28px;
    }
  `,
  l: css`
    span {
      font-size: 1.4rem;
    }
    svg {
      width: 32px;
      height: 32px;
    }
  `
};

// ButtonOutline
export const ButtonOutlineColorStyles = {
  Normal: css`
    border: 1px solid var(--gray-100);
    color: var(--gray-500);
  `,
  Primary: css`
    border: 1px solid var(--main-color);
    color: var(--black-color);
  `,
  SubFirst: css`
    border: 1px solid var(--sub-color-1);
    color: var(--black-color);
  `,
  SubSecond: css`
    border: 1px solid var(--sub-color-2);
    color: var(--black-color);
  `,
  Danger: css`
    border: 1px solid var(--danger-color);
    color: var(--black-color);
  `,
  Success: css`
    border: 1px solid var(--success-color);
    color: var(--black-color);
  `
};
