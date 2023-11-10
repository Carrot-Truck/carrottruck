import { UnselectWrapperWrapper } from "./style";
import back from "../../../assets/icons/keyboard-backspace.svg";

interface IUnselectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
  setSidoName: React.Dispatch<React.SetStateAction<string>>;
  setSigunguName: React.Dispatch<React.SetStateAction<string>>;
}

function UnselectAddress({
  sidoId,
  setSidoId,
  sigunguId,
  setSigunguId,
  setSidoName,
  setSigunguName,
}: IUnselectAddressProps) {
  const handleUnselectClick = () => {
    if (sigunguId != null) {
      setSigunguId(null);
      setSidoName("");
    } else if (sidoId != null) {
      setSidoId(null);
      setSigunguName("");
    }
  };

  return (
    <UnselectWrapperWrapper onClick={handleUnselectClick}>
      <img src={back} alt="" />
    </UnselectWrapperWrapper>
  );
}

export default UnselectAddress;
