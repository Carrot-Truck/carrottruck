import Navbar from "components/organisms/Navbar";
import { SalePageLayout } from "./style";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Loading from "components/atoms/Loading";
import { getMenus } from "api/menu";
import { AxiosResponse } from "axios";
import MenuSelector from "components/organisms/MenuSelector";
import { isOpenFoodTruck } from "api/foodtruck/foodTruck";
import OrderDetailButton from "components/organisms/OrderDetailButton";

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
  const [isSaleDetailComp, setSaleDetailComp] = useState<boolean>(false);

  const location = useLocation();
  const navigate = useNavigate();

  const receivedData: IMenuItem[] = location.state && location.state.data;
  const selectedFoodTruck = Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0");

  const fetchData = () => {
    if (receivedData === null) {
      const requestParam: Object = {
        foodTruckId: selectedFoodTruck,
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

  const checkOnSale = () => {
    if (selectedFoodTruck === 0 || !selectedFoodTruck) {
      return;
    }

    isOpenFoodTruck(
      selectedFoodTruck,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data) {
          setLoading(false);
        } else {
          navigate("/");
          return;
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  useEffect(() => {
    fetchData();
    checkOnSale();
  }, []);

  return (
    <SalePageLayout>
      {loading ? (
        <Loading />
      ) : (
        <>
          <OrderDetailButton
            isSaleDetailComp={isSaleDetailComp}
            setSaleDetailComp={setSaleDetailComp}
          />
          <MenuSelector menuItemList={menuItemList} onSale={true} />
        </>
      )}
      <Navbar />
    </SalePageLayout>
  );
}

export default SalePage;
