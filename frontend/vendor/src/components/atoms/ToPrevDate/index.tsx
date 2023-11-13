import { ToPrevDateWrapper } from "./style";
import prev from "../../../assets/icons/prev_arrow.svg";

interface IToPrevDateProps {
  year: number;
  setYear: React.Dispatch<React.SetStateAction<number>>;
  month: number | null;
  setMonth: React.Dispatch<React.SetStateAction<number | null>>;
  nextActive: boolean;
  setNextActive: React.Dispatch<React.SetStateAction<boolean>>;
}

function ToPrevDate({
  year,
  setYear,
  month,
  setMonth,
  nextActive,
  setNextActive,
}: IToPrevDateProps) {
  const handlePrevClick = () => {
    if (month !== null) {
      if (month === 1) {
        setMonth(12);
        setYear(year - 1);
      } else {
        setMonth(month - 1);
      }
    } else {
      setYear(year - 1);
    }
    if (!nextActive) {
      setNextActive(true);
    }
  };

  return (
    <ToPrevDateWrapper onClick={handlePrevClick}>
      <img src={prev} alt="" />
    </ToPrevDateWrapper>
  );
}

export default ToPrevDate;
