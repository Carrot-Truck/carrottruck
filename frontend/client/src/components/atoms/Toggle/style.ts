import styled from 'styled-components';

export const ToggleWrapper = styled.div`
  position: absolute;
  top: 0%;
  right: 0%;

  *:before,
  *:after {
    content: '';
    position: absolute;
  }
  .toggle-wrapper {
    flex: 1 1 calc(100% / 3);
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    position: relative;
  }
  .toggle {
    position: relative;
    display: inline-block;
  }
  input {
    height: 10px;
    left: 0;
    opacity: 0;
    position: absolute;
    top: 0;
    width: 10px;
  }
  .normal label {
    background: #af4c4c;
    border: 0.5px solid rgba(117, 117, 117, 0.31);
  }
  #normal:checked + label:before {
    left: calc(100% - 0.6rem);
  }
  #normal:checked + label {
    background: var(--sub-color-1);
  }
  .normal label:before {
    border: none;
    width: 1rem;
    height: 1rem;
    background: #fff;
    transform: rotate(-25deg);
  }
  .normal label:after {
    background: transparent;
    height: calc(100% + 8px);
    border-radius: 30px;
    top: -5px;
    width: calc(100% + 8px);
    left: -4px;
    z-index: 0;
  }

  label.toggle-item {
    width: 3.5em;
    background: var(--main-color);
    height: 1.5em;
    display: inline-block;
    border-radius: 50px;
    margin: 20px 10px;
    position: relative;
    transition: all 0.3s ease;
    transform-origin: 20% center;
    cursor: pointer;
  }
  label.toggle-item:before {
    display: block;
    transition: all 0.2s ease;
    width: 1.15em;
    height: 1.15em;
    top: 51%;
    left: 19%;
    transform: translate(-50%, -50%);
    border-radius: 1.15em;
    border: 2px solid #88cf8f;
    transition: 0.3s ease;
  }
`;
