import Loading from "components/atoms/Loading";
import { StatisticsListContainer } from "./style";
import { useState, useEffect, useRef } from "react";
import { getStatisticsByMonth, getStatisticsBySales, getStatisticsByWeek } from "api/statistics";
import { AxiosResponse } from "axios";
import NothingHere from "components/atoms/NothingHere";
import StatisticsSalesItem from "components/atoms/StatisticsSalesItem";
import StatisticsWeekItem from "components/atoms/StatisticsWeekItem";
import StatisticsMonthItem from "components/atoms/StatisticsMonthItem";

interface IStatisticsListProps {
  selectedCriteria: string;
  year: number;
  month: number | null;
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

interface SalesListRequest {
  year: number;
  month: number | null;
  lastSalesId: number | null;
}

interface WeekListRequest {
  year: number;
  lastWeek: number | null;
}

interface MonthListRequest {
  year: number;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function StatisticsList({ selectedCriteria, year, month }: IStatisticsListProps) {
  const [loading, setLoading] = useState<boolean>(true);
  const [bySalesItemList, setBySalesItemList] = useState<IBySalesItem[]>([]);
  const [byWeekItemList, setByWeekItemList] = useState<IByWeekItem[]>([]);
  const [byMonthItemList, setByMonthItemList] = useState<IByMonthItem[]>([]);
  const [lastSalesId, setLastSalesId] = useState<number | null>(null);
  const [lastWeek, setLastWeek] = useState<number | null>(null);
  const [hasNext, setHasNext] = useState<boolean>(true);
  const [infScrollLoading, setInfScrollLoading] = useState<boolean>(false);
  const [infScrollDone, setInfScrollDone] = useState<boolean>(false);

  const infScrollTargetRef: React.MutableRefObject<any> = useRef(null);

  const observerOptions = {
    root: null,
    rootMargin: "5px",
    threshold: 0.8,
  };

  const infScrollLoadMore = async () => {
    if (infScrollLoading || !hasNext) return;
    setInfScrollLoading(true);

    if (selectedCriteria === "영업") {
      getBySales();
    } else if (selectedCriteria === "주") {
      getByWeek();
    }
  };

  const getBySales = async () => {
    const requestParams: SalesListRequest = {
      year: year,
      month: month,
      lastSalesId: lastSalesId,
    };

    getStatisticsBySales(
      1,
      requestParams,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (!data["hasNext"]) {
          setHasNext(data["hasNext"]);
          setInfScrollDone(true);
        }
        setLastSalesId(data["lastSalesId"]);
        setBySalesItemList(bySalesItemList.concat(data["statisticsBySales"]));
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
        setLoading(false);
      }
    );
  };

  const getByWeek = async () => {
    const requestParams: WeekListRequest = {
      year: year,
      lastWeek: lastWeek,
    };

    getStatisticsByWeek(
      1,
      requestParams,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (!data["hasNext"]) {
          setHasNext(data["hasNext"]);
          setInfScrollDone(true);
        }
        setLastWeek(data["lastWeek"]);
        setByWeekItemList(byWeekItemList.concat(data["statisticsByWeek"]));
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
        setLoading(false);
      }
    );
  };

  const getByMonth = async () => {
    const requestParams: MonthListRequest = {
      year: year,
    };

    getStatisticsByMonth(
      1,
      requestParams,
      (response: AxiosResponse) => {
        const data = getData(response);
        setByMonthItemList(data["statisticsByMonth"]);
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const handleSalesItemClick = (foodTruckId: number, salesId: number) => {
    // navigate with params
    console.log(`To Sales Detail Page, fid: ${foodTruckId}, salesId: ${salesId}`);
  };

  const handleWeekItemClick = (foodTruckId: number, startDate: string, endDate: string) => {
    // navigate with params
    console.log(
      `To Weekly Detail Page, fid: ${foodTruckId}, startDate: ${startDate}, endDate: ${endDate}`
    );
  };

  const handleMonthItemClick = (foodTruckId: number, year: number, month: number) => {
    // navigate with params
    console.log(`To Montly Detail Page, fid: ${foodTruckId}, year: ${year}, month: ${month}`);
  };

  useEffect(() => {
    if (loading || infScrollDone) return;

    const infScrollReloadCallback = async (entries: any) => {
      if (!infScrollLoading && entries[0].isIntersecting) {
        await infScrollLoadMore().finally(() => {
          setInfScrollLoading(false);
        });
      }
    };

    const infScrollObserver = new IntersectionObserver(infScrollReloadCallback, observerOptions);

    infScrollObserver.observe(infScrollTargetRef.current);

    return () => {
      infScrollObserver.disconnect();
    };
  }, [loading, infScrollLoading, lastSalesId, lastWeek]);

  useEffect(() => {
    setBySalesItemList([]);
    setByWeekItemList([]);
    setLoading(true);
    setInfScrollDone(false);
    setInfScrollLoading(false);
    setLastSalesId(null);
    setLastWeek(null);
    setHasNext(true);
    if (selectedCriteria === "영업" && month !== null) {
      getBySales();
    } else if (selectedCriteria === "주" && month === null) {
      getByWeek();
    } else if (selectedCriteria === "월" && month === null) {
      getByMonth();
    }
  }, [selectedCriteria, month, year]);

  return (
    <StatisticsListContainer>
      {loading ? (
        <Loading />
      ) : selectedCriteria === "영업" ? (
        bySalesItemList.length === 0 ? (
          <NothingHere />
        ) : (
          bySalesItemList.map((data: IBySalesItem, index: number) => (
            <StatisticsSalesItem
              key={index}
              date={data.date}
              startTime={data.startTime}
              endTime={data.endTime}
              address={data.address}
              totalHours={data.totalHours}
              totalMinutes={data.totalMinutes}
              totalSales={data.totalSales}
              handleSalesItemClick={() => handleSalesItemClick(1, data.salesId)}
            />
          ))
        )
      ) : selectedCriteria === "주" ? (
        byWeekItemList.length === 0 ? (
          <NothingHere />
        ) : (
          byWeekItemList.map((data: IByWeekItem, index: number) => (
            <StatisticsWeekItem
              key={index}
              startDate={data.startDate}
              endDate={data.endDate}
              totalHours={data.totalHours}
              totalMinutes={data.totalMinutes}
              totalSales={data.totalSales}
              handleWeekItemClick={() => handleWeekItemClick(1, data.startDate, data.endDate)}
            />
          ))
        )
      ) : selectedCriteria === "월" ? (
        byMonthItemList.length === 0 ? (
          <NothingHere />
        ) : (
          byMonthItemList.map((data: IByMonthItem, index: number) => (
            <StatisticsMonthItem
              key={index}
              year={year}
              month={data.month}
              totalHours={data.totalHours}
              totalMinutes={data.totalMinutes}
              totalSales={data.totalSales}
              handleMonthItemClick={() => handleMonthItemClick(1, year, data.month)}
            />
          ))
        )
      ) : (
        <NothingHere />
      )}
      {infScrollLoading ? (
        <Loading />
      ) : (
        !infScrollDone && (
          <div style={{ height: "50px", backgroundColor: "white" }} ref={infScrollTargetRef}></div>
        )
      )}
    </StatisticsListContainer>
  );
}

export default StatisticsList;
