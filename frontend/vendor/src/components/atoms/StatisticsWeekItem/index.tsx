import { StatisticsWeekItemWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

interface IStatisticsWeekItemProps {
  startDate: string;
  endDate: string;
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
  handleWeekItemClick?: () => void;
  inDetail?: boolean;
}

function StatisticsWeekItem({
  startDate,
  endDate,
  totalHours,
  totalMinutes,
  totalSales,
  handleWeekItemClick,
  inDetail,
}: IStatisticsWeekItemProps) {
  return (
    <StatisticsWeekItemWrapper>
      <div className="week-left-div">
        <div className="week-date">
          {startDate} ~ {endDate}
        </div>
        <div>
          총 {totalHours}시간 {totalMinutes}분
        </div>
      </div>
      <div className="week-right-div">
        {!inDetail && (
          <div className="next_arrow_wrapper" onClick={handleWeekItemClick}>
            <img src={next} alt="" />
          </div>
        )}
        <div className="week-total-sale">{totalSales.toLocaleString()}원</div>
      </div>
    </StatisticsWeekItemWrapper>
  );
}

export default StatisticsWeekItem;
