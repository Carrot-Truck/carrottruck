import { SalesByMenuItemWrapper } from "./style";

interface ISalesByMenuItemProps {
  totalOrders: number;
  totalSales: number;
  menuName: string;
}

function SalesByMenuItem({ totalOrders, totalSales, menuName }: ISalesByMenuItemProps) {
  return (
    <SalesByMenuItemWrapper>
      <div className="menu-item-menu-name">{menuName}</div>
      <div className="menu-item-menu-orders">{totalOrders}개</div>
      <div className="menu-item-menu-sales">{totalSales.toLocaleString()}원</div>
    </SalesByMenuItemWrapper>
  );
}

export default SalesByMenuItem;
