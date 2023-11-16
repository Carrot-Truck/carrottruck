import SalesByMenuItem from "components/atoms/SalesByMenuItem";
import { SalesByMenuContainer } from "./style";

interface ISalesByMenu {
  totalOrders: number;
  totalSales: number;
  menuId: number;
  menuName: string;
}

interface ISalesByMenuProps {
  salesByMenu: ISalesByMenu[];
}

function SalesByMenu({ salesByMenu }: ISalesByMenuProps) {
  return (
    <SalesByMenuContainer>
      <>
        {salesByMenu.map((data: ISalesByMenu, index: number) => (
          <SalesByMenuItem
            key={index}
            totalOrders={data.totalOrders}
            totalSales={data.totalSales}
            menuName={data.menuName}
          />
        ))}
      </>
    </SalesByMenuContainer>
  );
}

export default SalesByMenu;
