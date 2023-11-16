import { UnselectAddressWrapper } from "./style";
import back from "../../../assets/icons/keyboard-backspace.svg";
import { useNavigate } from "react-router-dom";

interface IUnselectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
  dongId: number | null;
  setDongId: React.Dispatch<React.SetStateAction<number | null>>;
  categoryId: number | null;
  setCategoryId: React.Dispatch<React.SetStateAction<number | null>>;
}

function UnselectAddress({
  sidoId,
  setSidoId,
  sigunguId,
  setSigunguId,
  dongId,
  setDongId,
  categoryId,
  setCategoryId,
}: IUnselectAddressProps) {
  const navigate = useNavigate();

  const handleUnselectClick = () => {
    if (sidoId == null) {
      navigate(-1);
    } else if (categoryId != null) {
      setCategoryId(null);
    } else if (dongId != null) {
      setDongId(null);
    } else if (sigunguId != null) {
      setSigunguId(null);
    } else if (sidoId != null) {
      setSidoId(null);
    }
  };

  return (
    <UnselectAddressWrapper onClick={handleUnselectClick}>
      <img src={back} alt="" />
    </UnselectAddressWrapper>
  );
}

export default UnselectAddress;
