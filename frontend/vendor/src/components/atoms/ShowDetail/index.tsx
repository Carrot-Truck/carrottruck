import { ShowDetailWrapper } from "./style";
import up from "../../../assets/icons/up_arrow.svg";
import down from "../../../assets/icons/down_arrow.svg";

interface IShowDetailProps {
  viewable: boolean;
  setViewable: React.Dispatch<React.SetStateAction<boolean>>;
}

function ShowDetail({ viewable, setViewable }: IShowDetailProps) {
  const handleShowDetailClick = () => {
    setViewable(!viewable);
  };

  return (
    <ShowDetailWrapper onClick={handleShowDetailClick}>
      {viewable ? <img src={up} alt="" /> : <img src={down} alt="" />}
    </ShowDetailWrapper>
  );
}

export default ShowDetail;
