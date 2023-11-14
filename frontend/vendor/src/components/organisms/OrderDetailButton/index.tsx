import TitleText from "components/atoms/TitleText";
import { OrderDetailButtonContainer } from "./style";
import ToOrderDetail from "components/atoms/ToOrderDetail";

interface IOrderDetailButtonProps {
  isSaleDetailComp: boolean;
  setSaleDetailComp: React.Dispatch<React.SetStateAction<boolean>>;
}

function OrderDetailButton({ isSaleDetailComp, setSaleDetailComp }: IOrderDetailButtonProps) {
  return (
    <OrderDetailButtonContainer>
      <TitleText text={"주문 상세 보기"} size={"l"} textAlign={"center"} />
      <ToOrderDetail isSaleDetailComp={isSaleDetailComp} setSaleDetailComp={setSaleDetailComp} />
    </OrderDetailButtonContainer>
  );
}

export default OrderDetailButton;
