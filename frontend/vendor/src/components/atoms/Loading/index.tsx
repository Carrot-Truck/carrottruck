import { LoadingWrapper } from "./style";

function Loading() {
  return (
    <LoadingWrapper>
      <div className="lds-dual-ring"></div>
    </LoadingWrapper>
  );
}

export default Loading;
