import ToPrevDate from "components/atoms/ToPrevDate";
import { StatisticsDateSelectorContainer } from "./style";
import ToNextDate from "components/atoms/ToNextDate";
import StatisticsDateText from "components/atoms/StatisticsDateText";
import { useState } from "react";

interface IStatisticsDateSelector {
  year: number;
  setYear: React.Dispatch<React.SetStateAction<number>>;
  month: number | null;
  setMonth: React.Dispatch<React.SetStateAction<number | null>>;
}

function StatisticsDateSelector({ year, setYear, month, setMonth }: IStatisticsDateSelector) {
  const [nextActive, setNextActive] = useState<boolean>(false);

  return (
    <StatisticsDateSelectorContainer>
      <ToPrevDate
        year={year}
        setYear={setYear}
        month={month}
        setMonth={setMonth}
        nextActive={nextActive}
        setNextActive={setNextActive}
      />
      <StatisticsDateText year={year} month={month} />
      <ToNextDate
        year={year}
        setYear={setYear}
        month={month}
        setMonth={setMonth}
        nextActive={nextActive}
        setNextActive={setNextActive}
      />
    </StatisticsDateSelectorContainer>
  );
}

export default StatisticsDateSelector;
