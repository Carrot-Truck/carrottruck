import Navbar from "components/organisms/Navbar";
import { StatisticsLayout } from "./style";
import { useEffect, useState } from "react";
import StatisticsCriteriaBar from "components/organisms/StatisticsCriteriaBar";
import StatisticsDateSelector from "components/organisms/StatisticsDateSelector";
import StatisticsList from "components/organisms/StatisticsList";

function StatisticsPage() {
  const [selectedCriteria, setSelectedCriteria] = useState<string>("영업");
  const [selectedYear, setSelectedYear] = useState<number>(new Date().getFullYear());
  const [selectedMonth, setSelectedMonth] = useState<number | null>(null);

  useEffect(() => {
    const curYear = new Date().getFullYear();
    const curMonth = new Date().getMonth() + 1;

    setSelectedYear(curYear);
    if (selectedCriteria === "영업" && selectedMonth === null) {
      setSelectedMonth(curMonth);
    } else {
      setSelectedMonth(null);
    }
  }, [selectedCriteria]);

  return (
    <StatisticsLayout>
      <StatisticsCriteriaBar
        selectedCriteria={selectedCriteria}
        setSelectedCriteria={setSelectedCriteria}
      />
      <StatisticsDateSelector
        year={selectedYear}
        setYear={setSelectedYear}
        month={selectedMonth}
        setMonth={setSelectedMonth}
      />
      <StatisticsList
        selectedCriteria={selectedCriteria}
        year={selectedYear}
        month={selectedMonth}
      />
      <Navbar />
    </StatisticsLayout>
  );
}

export default StatisticsPage;
