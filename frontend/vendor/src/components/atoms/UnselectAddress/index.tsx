import { UnselectWrapperWrapper } from "./style";
import back from "../../../assets/icons/keyboard-backspace.svg";

interface IUnselectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
}

function UnselectAddress({ sidoId, setSidoId, sigunguId, setSigunguId }: IUnselectAddressProps) {
  const handleUnselectClick = () => {
    if (sigunguId != null) {
      setSigunguId(null);
    } else if (sidoId != null) {
      setSidoId(null);
    }
  };

  return (
    <UnselectWrapperWrapper onClick={handleUnselectClick}>
      <img src={back} alt="" />
    </UnselectWrapperWrapper>
  );
}

export default UnselectAddress;
