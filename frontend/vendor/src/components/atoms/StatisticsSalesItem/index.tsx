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
  handleSalesItemClick?: () => void;
  inDetail?: boolean;
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
  inDetail,
}: IStatisticsSalesItemProps) {
  return (
    <StatisticsSalesItemWrapper>
      <div className="sales-left-div">
        <div className="sales-date">{date}</div>
        <div className="sales-time">
          {startTime} ~ {endTime}
        </div>
        <div>
          총 {totalHours}시간 {totalMinutes}분
        </div>
        {!inDetail && <div>{address}</div>}
      </div>
      <div className="sales-right-div">
        {!inDetail && (
          <div className="next_arrow_wrapper" onClick={handleSalesItemClick}>
            <img src={next} alt="" />
          </div>
        )}
        <div className="sales-total-sale">{totalSales.toLocaleString()}원</div>
      </div>
    </StatisticsSalesItemWrapper>
  );
}

export default StatisticsSalesItem;
