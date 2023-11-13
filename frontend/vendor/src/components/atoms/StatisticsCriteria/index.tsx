import { StatisticsCriteriaWrapper } from "./style";

interface IStatisticsCriteriaProps {
  criteria: string;
  selectedCriteria: string;
  setSelectedCriteria: React.Dispatch<React.SetStateAction<string>>;
}

function StatisticsCriteria({
  criteria,
  selectedCriteria,
  setSelectedCriteria,
}: IStatisticsCriteriaProps) {
  const handleCriteriaClick = () => {
    if (criteria !== selectedCriteria) {
      setSelectedCriteria(criteria);
    }
  };

  return (
    <StatisticsCriteriaWrapper
      onClick={handleCriteriaClick}
      selected={criteria === selectedCriteria}
    >
      {criteria}
    </StatisticsCriteriaWrapper>
  );
}

export default StatisticsCriteria;
