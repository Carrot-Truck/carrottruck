import { ToggleWrapper } from './style';

function Toggle() {
  return (
    <ToggleWrapper>
      <div className="toggle-wrapper">
        <div className="toggle normal">
          <input id="normal" type="checkbox" />
          <label className="toggle-item" htmlFor="normal"></label>
        </div>
      </div>
    </ToggleWrapper>
  );
}

export default Toggle;
