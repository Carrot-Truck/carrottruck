import { SalesByDayItemWrapper } from "./style";

interface ISalesByDayProps {
  totalOrders: number;
  totalSales: number;
  day: string;
}

function SalesByDayItem({ totalOrders, totalSales, day }: ISalesByDayProps) {
  return (
    <SalesByDayItemWrapper>
      <div className="day-item-day-name">{day}요일</div>
      <div className="day-item-day-orders">{totalOrders}개</div>
      <div className="day-item-day-sales">{totalSales.toLocaleString()}원</div>
    </SalesByDayItemWrapper>
  );
}

export default SalesByDayItem;
