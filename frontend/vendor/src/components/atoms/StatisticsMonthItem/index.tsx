import { StatisticsMonthItemWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

interface IStatisticsMonthItemProps {
  year: number;
  month: number;
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
  handleMonthItemClick?: () => void;
  inDetail?: boolean;
}

function StatisticsMonthItem({
  year,
  month,
  totalHours,
  totalMinutes,
  totalSales,
  handleMonthItemClick,
  inDetail,
}: IStatisticsMonthItemProps) {
  return (
    <StatisticsMonthItemWrapper>
      <div className="month-left-div">
        <div className="month-date">
          {year}년 {month}월
        </div>
        <div>
          총 {totalHours}시간 {totalMinutes}분
        </div>
      </div>
      <div className="month-right-div">
        {!inDetail && (
          <div className="next_arrow_wrapper" onClick={handleMonthItemClick}>
            <img src={next} alt="" />
          </div>
        )}
        <div className="month-total-sale">{totalSales.toLocaleString()}원</div>
      </div>
    </StatisticsMonthItemWrapper>
  );
}

export default StatisticsMonthItem;
