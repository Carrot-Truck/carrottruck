import { StatisticsWeekItemWrapper } from "./style";
import next from "../../../assets/icons/next_arrow.svg";

interface IStatisticsWeekItemProps {
  startDate: string;
  endDate: string;
  totalHours: number;
  totalMinutes: number;
  totalSales: number;
  handleWeekItemClick: () => void;
}

function StatisticsWeekItem({
  startDate,
  endDate,
  totalHours,
  totalMinutes,
  totalSales,
  handleWeekItemClick,
}: IStatisticsWeekItemProps) {
  return (
    <StatisticsWeekItemWrapper>
      <div className="week-item">
        <div className="week-left-div">
          <p>
            {startDate} ~ {endDate}
          </p>
          <p>
            총 {totalHours}시간 {totalMinutes}분
          </p>
        </div>
        <div className="week-right-div">
          <div className="next_arrow_wrapper" onClick={handleWeekItemClick}>
            <img src={next} alt="" />
          </div>
          <p>{totalSales}원</p>
        </div>
      </div>
    </StatisticsWeekItemWrapper>
  );
}

export default StatisticsWeekItem;
