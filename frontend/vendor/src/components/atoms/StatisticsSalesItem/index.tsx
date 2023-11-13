import { StatisticsSalesItemWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

interface IStatisticsSalesItemProps {
  date: string;
  startTime: string;
  endTime: string;
  address: string;
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
  handleSalesItemClick: () => void;
}

function StatisticsSalesItem({
  date,
  startTime,
  endTime,
  address,
  totalHours,
  totalMinutes,
  totalSales,
  handleSalesItemClick,
}: IStatisticsSalesItemProps) {
  return (
    <StatisticsSalesItemWrapper>
      <div className="sales-item">
        <div className="sales-left-div">
          <p>{date}</p>
          <p>
            {startTime} ~ {endTime}
          </p>
          <p>
            총 {totalHours}시간 {totalMinutes}분
          </p>
          <p>{address}</p>
        </div>
        <div className="sales-right-div">
          <div className="next_arrow_wrapper" onClick={handleSalesItemClick}>
            <img src={next} alt="" />
          </div>
          <p>{totalSales}원</p>
        </div>
      </div>
    </StatisticsSalesItemWrapper>
  );
}

export default StatisticsSalesItem;
