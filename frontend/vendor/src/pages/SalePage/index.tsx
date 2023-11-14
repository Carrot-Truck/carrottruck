import Navbar from "components/organisms/Navbar";
import { SalePageLayout } from "./style";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import Loading from "components/atoms/Loading";
import { getMenus } from "api/menu";
import { AxiosResponse } from "axios";
import MenuSelector from "components/organisms/MenuSelector";

interface IMenuItem {
  menuId: number;
  menuName: string;
  menuPrice: number;
  menuDescription: string;
  menuSoldOut: boolean;
  menuImageUrl: string;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function SalePage() {
  const [loading, setLoading] = useState<boolean>(true);
  const [menuItemList, setMenuItemList] = useState<IMenuItem[]>([]);

  const location = useLocation();

  const receivedData: IMenuItem[] = location.state && location.state.data;
  const foodTruckId = Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0");

  const fetchData = () => {
    if (receivedData === null) {
      const requestParam: Object = {
        foodTruckId: foodTruckId,
      };

      getMenus(
        requestParam,
        (response: AxiosResponse) => {
          const data = getData(response);
          setMenuItemList(data["menus"]);
        },
        (error: any) => {
          console.log(error);
        }
      );
    } else {
      setMenuItemList(receivedData);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <SalePageLayout>
      {loading ? (
        <Loading />
      ) : (
        <>
          <MenuSelector menuItemList={menuItemList} onSale={true} />
        </>
      )}
      <Navbar />
    </SalePageLayout>
  );
}

export default SalePage;
