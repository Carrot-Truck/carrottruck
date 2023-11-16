import Navbar from "components/organisms/Navbar";
import { StartSalePageLayout } from "./style";
import NaverMap from "components/atoms/Map";
import { useState, useEffect } from "react";
import Loading from "components/atoms/Loading";
import { reverseGeocoding } from "api/address";
import { AxiosResponse } from "axios";
import TitleText from "components/atoms/TitleText";
import MenuSelector from "components/organisms/MenuSelector";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router-dom";
import { openSale, setSoldout } from "api/sale";
import { getMenus } from "api/menu";
import { isOpenFoodTruck } from "api/foodtruck/foodTruck";
import BackSpace from "components/atoms/BackSpace";

interface ICoords {
  latitude: number;
  longitude: number;
}

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

const getMenuIds = (items: IMenuItem[]) => {
  const ids = [];
  for (const item of items) {
    if (item.menuSoldOut) continue;
    const obj = {
      menuId: item.menuId,
      menuOptionId: [],
    };
    ids.push(obj);
  }
  return ids;
};

function StartSalePage() {
  const [loading, setLoading] = useState<boolean>(true);
  const [coords, setCoords] = useState<ICoords | null>(null);
  const [curAddress, setCurAddress] = useState<string>("");
  const [menuItemList, setMenuItemList] = useState<IMenuItem[]>([]);

  const navigate = useNavigate();

  const clientId: string = process.env.REACT_APP_CLIENT_ID || "";
  const selectedFoodTruck = Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0");

  const getCurrentLocation = () => {
    const curCoords: ICoords = {
      latitude: 0,
      longitude: 0,
    };

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          curCoords.latitude = latitude;
          curCoords.longitude = longitude;
          setCoords(curCoords);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  };

  const fetchData = async () => {
    const requestParam: Object = {
      foodTruckId: Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0"),
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
          navigate("/sale");
          return;
        } else {
          setLoading(false);
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  useEffect(() => {
    getCurrentLocation();
    fetchData();
    checkOnSale();
  }, []);

  useEffect(() => {
    if (coords === null) return;
    reverseGeocoding(
      coords,
      (response: AxiosResponse) => {
        const data = getData(response);
        setCurAddress(data["address"]);
      },
      (error: any) => {
        console.log(error);
      }
    );
  }, [coords]);

  const setSoldOut = () => {
    for (const item of menuItemList) {
      if (item.menuSoldOut) {
        setSoldout(
          item.menuId,
          (response: AxiosResponse) => {
            getData(response);
          },
          (error: any) => {
            console.log(error);
          }
        );
      }
    }
  };

  const startSale = () => {
    if (menuItemList.length === 0) {
      alert("메뉴를 등록해주세요");
      return;
    }

    const requestBody = {
      foodTruckId: localStorage.getItem("selectedFoodTruckId"),
      address: curAddress,
      latitude: coords?.latitude,
      longitude: coords?.longitude,
      saleMenuItems: getMenuIds(menuItemList),
    };

    if (requestBody.saleMenuItems.length === 0) {
      alert("메뉴를 선택해주세요");
      return;
    }

    openSale(
      requestBody,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (!data) {
          alert("영업을 시작할 수 없어요.");
          navigate(-1);
        } else {
          navigate("/sale", { state: { data: menuItemList } });
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  return (
    <StartSalePageLayout>
      <div className="hdaer">
        <BackSpace></BackSpace>
      </div>
      {loading ? (
        <Loading />
      ) : (
        <>
          <div className="map-container">
            <NaverMap clientId={clientId} markers={[]} dynamicheight={"50vh"} />
          </div>
          <TitleText text={curAddress} size="s" textAlign="center" />
          <MenuSelector
            menuItemList={menuItemList}
            setMenuItemList={setMenuItemList}
            onSale={false}
          />
          <div className="start-sale-button-container">
            <Button
              size={"m"}
              radius={"s"}
              color="Primary"
              text="영업시작"
              handleClick={() => {
                setSoldOut();
                startSale();
              }}
            />
          </div>
        </>
      )}
      <Navbar />
    </StartSalePageLayout>
  );
}

export default StartSalePage;
