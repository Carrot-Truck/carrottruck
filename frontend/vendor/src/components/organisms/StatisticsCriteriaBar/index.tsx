import StatisticsCriteria from "components/atoms/StatisticsCriteria";
import { StatisticsCriteriaContainer } from "./style";

interface IStatisticsCriteriaBarProps {
  selectedCriteria: string;
  setSelectedCriteria: React.Dispatch<React.SetStateAction<string>>;
}

function StatisticsCriteriaBar({
  selectedCriteria,
  setSelectedCriteria,
}: IStatisticsCriteriaBarProps) {
  const criteria: string[] = ["영업", "주", "월"];

  return (
    <StatisticsCriteriaContainer>
      {criteria.map((criteria: string, index: number) => (
        <StatisticsCriteria
          key={index}
          criteria={criteria}
          selectedCriteria={selectedCriteria}
          setSelectedCriteria={setSelectedCriteria}
        />
      ))}
    </StatisticsCriteriaContainer>
  );
}

export default StatisticsCriteriaBar;
