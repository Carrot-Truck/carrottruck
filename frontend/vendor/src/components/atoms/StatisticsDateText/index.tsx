import { StatisticsDateTextWrapper } from "./style";

interface IStatisticsDateTextProps {
  year: number;
  month: number | null;
}

function StatisticsDateText({ year, month }: IStatisticsDateTextProps) {
  return (
    <StatisticsDateTextWrapper>
      {year}년 {month && `${month}월`}
    </StatisticsDateTextWrapper>
  );
}

export default StatisticsDateText;
