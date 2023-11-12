import { ToNextDateWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";
import { useEffect } from "react";

interface IToNextDateProps {
  year: number;
  setYear: React.Dispatch<React.SetStateAction<number>>;
  month: number | null;
  setMonth: React.Dispatch<React.SetStateAction<number | null>>;
  nextActive: boolean;
  setNextActive: React.Dispatch<React.SetStateAction<boolean>>;
}

function ToNextDate({
  year,
  setYear,
  month,
  setMonth,
  nextActive,
  setNextActive,
}: IToNextDateProps) {
  const curYear = new Date().getFullYear();
  const curMonth = new Date().getMonth() + 1;

  const handleNextClick = () => {
    if (month !== null) {
      if (month === 12) {
        setMonth(1);
        setYear(year + 1);
      } else {
        setMonth(month + 1);
      }
    } else {
      setYear(year + 1);
    }
  };

  useEffect(() => {
    if (month !== null) {
      if (year >= curYear && month >= curMonth) {
        setNextActive(false);
      }
    } else {
      if (year >= curYear) {
        setNextActive(false);
      }
    }
  }, [year, month]);

  return (
    <>
      {nextActive ? (
        <ToNextDateWrapper onClick={handleNextClick}>
          <img src={next} alt="" />
        </ToNextDateWrapper>
      ) : (
        <ToNextDateWrapper />
      )}
    </>
  );
}

export default ToNextDate;
