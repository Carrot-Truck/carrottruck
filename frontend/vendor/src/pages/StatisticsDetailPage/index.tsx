import Navbar from "components/organisms/Navbar";
import { StatisticsDetailLayout } from "./style";
import BackSpace from "components/atoms/BackSpace";
import { useLocation } from "react-router-dom";

const getParams = (queryString: string): Record<string, any> => {
  const params = new URLSearchParams(queryString);
  const result: Record<string, any> = {};

  params.forEach((value, key) => {
    result[key] = value;
  });

  return result;
};

function StatisticsDetailPage() {
  const location = useLocation();
  const params: any = getParams(location.search);

  console.log(params);

  return (
    <StatisticsDetailLayout>
      <BackSpace />
      statistics Detail page
      <Navbar />
    </StatisticsDetailLayout>
  );
}

export default StatisticsDetailPage;
