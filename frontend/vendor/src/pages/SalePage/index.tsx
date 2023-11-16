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
import ReturnToSaleMain from "components/atoms/ReturnToSaleMain";
import Button from "components/atoms/Button";
import { closeSale, pause, restart } from "api/sale";
import SwitchButton from "components/organisms/SwitchButton";
import OrderHistoryList from "components/organisms/OrderHistoryList";
import { getInfo } from "api/member/vendor";

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
  const [pausing, setPausing] = useState<boolean>(false);
  const [selectedOrderHistory, setSelectedOrderHistory] = useState<number>(1);
  const [vendorEmail, setVendorEmail] = useState<string>("");

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

    getInfo(
      (response: AxiosResponse) => {
        const data = getData(response);
        setVendorEmail(data["email"]);
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const checkOnSale = () => {
    if (selectedFoodTruck === 0 || !selectedFoodTruck) {
      return;
    }
    setSaleDetailComp(JSON.parse(localStorage.getItem("isSaleDetailComp") || "false"));

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

  const handleSalePauseClick = () => {
    if (
      !pausing &&
      window.confirm("영업을 일시 정지할까요?\n(정지한 동안 주문을 받을 수 없어요.)")
    ) {
      pause(
        selectedFoodTruck,
        (response: AxiosResponse) => {
          if (response.data.code === 200) {
            setPausing(true);
            alert("영업을 일시 정지했어요.");
          }
        },
        (error: any) => {
          console.log(error);
          alert("오류로 영업을 정지할 수 없어요");
        }
      );
    } else if (pausing && window.confirm("영업을 다시 시작할까요?")) {
      restart(
        selectedFoodTruck,
        (response: AxiosResponse) => {
          if (response.data.code === 200) {
            setPausing(false);
            alert("영업을 재개했어요.");
          }
        },
        (error: any) => {
          console.log(error);
          alert("오류로 영업을 시작할 수 없어요");
        }
      );
    }
  };

  const handleSaleCloseClick = () => {
    if (window.confirm("영업을 종료할까요?")) {
      closeSale(
        selectedFoodTruck,
        (response: AxiosResponse) => {
          if (response.data.code === 200) {
            alert("영업이 성공적으로 종료됐어요!");
            navigate("/");
          }
        },
        (error: any) => {
          console.log(error);
          alert("오류로 영업을 종료할 수 없어요");
        }
      );
    }
  };

  useEffect(() => {
    fetchData();
    checkOnSale();
  }, []);

  useEffect(() => {
    localStorage.setItem("isSaleDetailComp", isSaleDetailComp.toString());
  }, [isSaleDetailComp]);

  return (
    <SalePageLayout>
      {loading ? (
        <Loading />
      ) : !isSaleDetailComp ? (
        <>
          <OrderDetailButton
            isSaleDetailComp={isSaleDetailComp}
            setSaleDetailComp={setSaleDetailComp}
          />
          <MenuSelector
            menuItemList={menuItemList}
            setMenuItemList={setMenuItemList}
            onSale={true}
          />
          <div className="button-wrapper">
            <Button
              size={"m"}
              radius={"s"}
              color={"Primary"}
              text={pausing ? "영업 재개" : "영업 일시 정지"}
              handleClick={handleSalePauseClick}
            />
          </div>
          <div className="button-wrapper">
            <Button
              size={"m"}
              radius={"s"}
              color={"Primary"}
              text={"영업 종료"}
              handleClick={handleSaleCloseClick}
            />
          </div>
        </>
      ) : (
        <>
          <ReturnToSaleMain
            isSaleDetailComp={isSaleDetailComp}
            setSaleDetailComp={setSaleDetailComp}
          />
          <SwitchButton
            selectedButton={selectedOrderHistory}
            setSelectedButton={setSelectedOrderHistory}
            firstButton={"주문내역"}
            secondButton={"완료내역"}
          />
          <OrderHistoryList selectedOrderHistory={selectedOrderHistory} vendorEmail={vendorEmail} />
        </>
      )}
      <Navbar />
    </SalePageLayout>
  );
}

export default SalePage;
