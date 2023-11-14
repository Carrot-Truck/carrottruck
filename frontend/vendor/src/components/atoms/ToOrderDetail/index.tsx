import { ToOrderDetailWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

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
      <img src={next} alt="" />
    </ToOrderDetailWrapper>
  );
}

export default ToOrderDetail;
