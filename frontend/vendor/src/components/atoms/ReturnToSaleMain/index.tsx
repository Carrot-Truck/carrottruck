import { ReturnToSaleMainWrapper } from "./style";
import back from "../../../assets/icons/keyboard-backspace.svg";

interface IUnselectAddressProps {
  isSaleDetailComp: boolean;
  setSaleDetailComp: React.Dispatch<React.SetStateAction<boolean>>;
}

function ReturnToSaleMain({ isSaleDetailComp, setSaleDetailComp }: IUnselectAddressProps) {
  const handleArrowtClick = () => {
    if (isSaleDetailComp) {
      setSaleDetailComp(!isSaleDetailComp);
    }
  };

  return (
    <ReturnToSaleMainWrapper onClick={handleArrowtClick}>
      <img src={back} alt="" />
    </ReturnToSaleMainWrapper>
  );
}

export default ReturnToSaleMain;
