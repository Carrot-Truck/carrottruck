import styled from 'styled-components';

export const ToggleWrapper = styled.div`
  *:before,
  *:after {
    content: '';
    position: absolute;
  }
  .toggle-wrapper {
    background: #a5fbf7;
    flex: 1 1 calc(100% / 3);
    min-height: 50vh;
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
    height: 40px;
    left: 0;
    opacity: 0;
    position: absolute;
    top: 0;
    width: 40px;
  }
  .normal label {
    background: #af4c4c;
    border: 0.5px solid rgba(117, 117, 117, 0.31);
    box-shadow: inset 0px 0px 4px 0px rgba(0, 0, 0, 0.2), 0 -3px 4px rgba(0, 0, 0, 0.15);
  }
  #normal:checked + label:before {
    left: 67px;
  }
  #normal:checked + label {
    background: #c00032;
  }
  .normal label:before {
    border: none;
    width: 2.5em;
    height: 2.5em;
    box-shadow: inset 0.5px -1px 1px rgba(0, 0, 0, 0.35);
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
    box-shadow: inset 0px 2px 4px -2px rgba(0, 0, 0, 0.2), 0px 1px 2px 0px rgba(151, 151, 151, 0.2);
  }

  label.toggle-item {
    width: 7em;
    background: #2e394d;
    height: 3em;
    display: inline-block;
    border-radius: 50px;
    margin: 40px;
    position: relative;
    transition: all 0.3s ease;
    transform-origin: 20% center;
    cursor: pointer;
  }
  label.toggle-item:before {
    display: block;
    transition: all 0.2s ease;
    width: 2.3em;
    height: 2.3em;
    top: 0.25em;
    left: 0.25em;
    border-radius: 2em;
    border: 2px solid #88cf8f;
    transition: 0.3s ease;
  }
`;
