import { StatisticsMonthItemWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

interface IStatisticsMonthItemProps {
  year: number;
  month: number;
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
  handleMonthItemClick: () => void;
}

function StatisticsMonthItem({
  year,
  month,
  totalHours,
  totalMinutes,
  totalSales,
  handleMonthItemClick,
}: IStatisticsMonthItemProps) {
  return (
    <StatisticsMonthItemWrapper>
      <div className="month-item">
        <div className="month-left-div">
          <p>
            {year}년 {month}월
          </p>
          <p>
            총 {totalHours}시간 {totalMinutes}분
          </p>
        </div>
        <div className="month-right-div">
          <div className="next_arrow_wrapper" onClick={handleMonthItemClick}>
            <img src={next} alt="" />
          </div>
          <p>{totalSales}원</p>
        </div>
      </div>
    </StatisticsMonthItemWrapper>
  );
}

export default StatisticsMonthItem;
