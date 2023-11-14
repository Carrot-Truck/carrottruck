import { ToOrderDetailWrapper } from "./style";
import prev from "../../../assets/icons/prev_arrow.svg";

interface IToOrderDetailWrapperProps {
  isSaleDetailComp: boolean;
  setSaleDetailComp: React.Dispatch<React.SetStateAction<boolean>>;
}

function ToOrderDetail({ isSaleDetailComp, setSaleDetailComp }: IToOrderDetailWrapperProps) {
  const handleArrowClick = () => {
    if (!isSaleDetailComp) {
      setSaleDetailComp(true);
    }
  };

  return (
    <ToOrderDetailWrapper onClick={handleArrowClick}>
      <img src={prev} alt="" />
    </ToOrderDetailWrapper>
  );
}

export default ToOrderDetail;
