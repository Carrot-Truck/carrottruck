import Navbar from "components/organisms/Navbar";
import { StatisticsDetailLayout } from "./style";
import BackSpace from "components/atoms/BackSpace";
import { useLocation } from "react-router-dom";
import { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import Loading from "components/atoms/Loading";
import {
  getStatisticsByMonthDetails,
  getStatisticsBySalesDetails,
  getStatisticsByWeekDetails,
} from "api/statistics";
import SalesMap from "components/atoms/SalesStatisticsMap";
import StatisticsMonthItem from "components/atoms/StatisticsMonthItem";
import StatisticsWeekItem from "components/atoms/StatisticsWeekItem";
import StatisticsSalesItem from "components/atoms/StatisticsSalesItem";
import SalesByMenu from "components/organisms/SalesByMenu";
import SalesByHour from "components/organisms/SalesByHour";
import SalesByDay from "components/organisms/SalesByDay";
import TitleText from "components/atoms/TitleText";

interface ITotalInfo {
  totalOrders: number;
  totalSales: number;
}
interface ISalesByMenu extends ITotalInfo {
  menuId: number;
  menuName: string;
}

interface ISalesByHour extends ITotalInfo {
  startHour: number;
}

interface ISalesByDay extends ITotalInfo {
  day: number;
}

interface ICoordinate {
  latitude: number;
  longitude: number;
}

interface IStats {
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
}

interface IBySalesItem extends IStats {
  salesId: number;
  date: string;
  startTime: string;
  endTime: string;
  address: string;
}

interface IByWeekItem extends IStats {
  startDate: string;
  endDate: string;
}

interface IByMonthItem extends IStats {
  month: number;
}

const getParams = (queryString: string): Record<string, any> => {
  const params = new URLSearchParams(queryString);
  const result: Record<string, any> = {};

  params.forEach((value, key) => {
    result[key] = value;
  });

  return result;
};

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function StatisticsDetailPage() {
  const [loading, setLoading] = useState<boolean>(true);
  const [byMenuData, setByMenuData] = useState<ISalesByMenu[]>([]);
  const [byHourData, setByHourData] = useState<ISalesByHour[]>([]);
  const [byDayData, setByDayData] = useState<ISalesByDay[]>([]);
  const [saleCoords, setSaleCoords] = useState<ICoordinate>({ latitude: 0, longitude: 0 });
  const [salesSummary, setSalesSummary] = useState<IBySalesItem | undefined>(undefined);
  const [weekSummary, setWeekSummary] = useState<IByWeekItem | undefined>(undefined);
  const [monthSummary, setMonthSummary] = useState<IByMonthItem | undefined>(undefined);

  const location = useLocation();
  const params: Record<string, any> = getParams(location.search);
  const clientId: string = process.env.REACT_APP_CLIENT_ID || "";

  useEffect(() => {
    if (params.criteria === "sales") {
      setSalesSummary(JSON.parse(localStorage.getItem("summaryTemp") || "{}"));
      getStatisticsBySalesDetails(
        params.foodTruckId,
        params.salesId,
        (response: AxiosResponse) => {
          const data = getData(response);
          setByMenuData(data["salesByMenu"]);
          setByHourData(data["salesByHour"]);
          setSaleCoords({ latitude: data["latitude"], longitude: data["longitude"] });
          setLoading(false);
        },
        (error: any) => {
          console.log(error);
        }
      );
    } else if (params.criteria === "week") {
      const requestParams = {
        startDate: params.startDate,
        endDate: params.endDate,
      };

      setWeekSummary(JSON.parse(localStorage.getItem("summaryTemp") || "{}"));
      getStatisticsByWeekDetails(
        params.foodTruckId,
        requestParams,
        (response: AxiosResponse) => {
          const data = getData(response);
          setByMenuData(data["salesByMenu"]);
          setByHourData(data["salesByHour"]);
          setByDayData(data["salesByDay"]);
          setLoading(false);
        },
        (error: any) => {
          console.log(error);
        }
      );
    } else if (params.criteria === "month") {
      const requestParams = {
        year: params.year,
        month: params.month,
      };

      setMonthSummary(JSON.parse(localStorage.getItem("summaryTemp") || "{}"));
      getStatisticsByMonthDetails(
        params.foodTruckId,
        requestParams,
        (response: AxiosResponse) => {
          const data = getData(response);
          setByMenuData(data["salesByMenu"]);
          setByHourData(data["salesByHour"]);
          setByDayData(data["salesByDay"]);
          setLoading(false);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }

    return () => {
      localStorage.removeItem("summaryTemp");
    };
  }, []);

  return (
    <StatisticsDetailLayout>
      <BackSpace />
      {loading ? (
        <Loading />
      ) : params.criteria === "sales" ? (
        <StatisticsSalesItem
          date={salesSummary?.date || ""}
          startTime={salesSummary?.startTime || ""}
          endTime={salesSummary?.endTime || ""}
          address={salesSummary?.address || ""}
          totalHours={salesSummary?.totalHours || 0}
          totalMinutes={salesSummary?.totalMinutes || 0}
          totalSales={salesSummary?.totalSales || 0}
          inDetail={true}
        />
      ) : params.criteria === "week" ? (
        <StatisticsWeekItem
          startDate={weekSummary?.startDate || ""}
          endDate={weekSummary?.endDate || ""}
          totalHours={weekSummary?.totalHours || 0}
          totalMinutes={weekSummary?.totalMinutes || 0}
          totalSales={weekSummary?.totalSales || 0}
          inDetail={true}
        />
      ) : params.criteria === "month" ? (
        <StatisticsMonthItem
          year={params?.year || 0}
          month={monthSummary?.month || 0}
          totalHours={monthSummary?.totalHours || 0}
          totalMinutes={monthSummary?.totalMinutes || 0}
          totalSales={monthSummary?.totalSales || 0}
          inDetail={true}
        />
      ) : (
        <></>
      )}
      {loading ? (
        <></>
      ) : params.criteria === "sales" ? (
        <>
          <SalesMap
            clientId={clientId}
            marker={{
              latitude: saleCoords.latitude,
              longitude: saleCoords.longitude,
            }}
            dynamicheight="50vh"
          />
          <p className="sales-address">{salesSummary?.address}</p>
        </>
      ) : (
        <></>
      )}
      {loading ? (
        <></>
      ) : (
        <>
          <TitleText text={"주문 항목"} size={"s"} textAlign={"left"} />
          <SalesByMenu salesByMenu={byMenuData} />
          <TitleText text={"시간대별 매출"} size={"s"} textAlign={"left"} />
          <SalesByHour salesByHour={byHourData} />
        </>
      )}
      {loading ? (
        <></>
      ) : params.criteria === "week" || params.criteria === "month" ? (
        <>
          <TitleText text={"요일별 매출"} size={"s"} textAlign={"left"} />
          <SalesByDay salesByDay={byDayData} />
        </>
      ) : (
        <></>
      )}
      <Navbar />
    </StatisticsDetailLayout>
  );
}

export default StatisticsDetailPage;
