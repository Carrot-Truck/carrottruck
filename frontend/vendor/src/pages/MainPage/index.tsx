import { LogoutP, MainPageLayout } from "./style";
import { useEffect, useState, useRef } from "react";
import Navbar from "components/organisms/Navbar";
import RegistFoodTruckButton from "components/organisms/RegistFoodTruckButton";
import VendorMainForm from "components/organisms/VendorMainForm";
import { AxiosResponse } from "axios";
import { useNavigate } from "react-router-dom";
import {
  getFoodTruckOverviews,
  isOpenFoodTruck,
} from "api/foodtruck/foodTruck";
import FoodTruckSelector from "components/organisms/FoodTruckSelector";
import FoodTruckList from "components/organisms/FoodTruckList";
import { getInfo } from "api/member/vendor";
import Loading from "components/atoms/Loading";

interface IFoodTruckListItem {
  foodTruckId: number;
  foodTruckName: string;
  selected: boolean;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function MainPage() {
  // 데이터가 비어있는지 여부를 확인할 상태 변수를 정의합니다.
  const [dataEmpty, setDataEmpty] = useState(true); // 초기값은 true로 설정합니다.
  const [isValid, setValid] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(true);
  const [detailViewable, setDetailViewable] = useState<boolean>(false);
  const [foodTruckList, setFoodTruckList] = useState<IFoodTruckListItem[]>([]);
  const [selectedFoodTruck, setSelectedFoodTruck] = useState<number | null>(
    null
  );
  const [selectedFoodTruckName, setSelectedFoodTruckName] =
    useState<string>("");

  const mainRef = useRef<HTMLDivElement>(null);

  const navigate = useNavigate();
  const accessToken = localStorage.getItem("accessToken");
  const grantType = localStorage.getItem("grantType");

  const modalOutSideClick = (e: any) => {
    if (mainRef.current === e.target) {
      setDetailViewable(false);
    }
  };

  const isValidUser = async () => {
    if (!accessToken || !grantType) {
      navigate("/login");
      return;
    }

    getInfo(
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data !== null) setValid(true);
      },
      (error: any) => {
        console.log(error);
        localStorage.clear();
        alert("다시 로그인 해야합니다.");
        navigate("/login");
      }
    );
  };

  const updateFoodTruckList = (update?: boolean) => {
    const requestParams: Object = {
      lastFoodTruckId: "",
    };

    getFoodTruckOverviews(
      requestParams,
      (response: AxiosResponse) => {
        const data = getData(response);
        if (data.length === 0) {
          setDataEmpty(true);
          return;
        } else {
          setDataEmpty(false);
        }
        setFoodTruckList(data["items"]);

        for (const item of data["items"]) {
          if (item["selected"]) {
            if (!update) setSelectedFoodTruck(item["foodTruckId"]);
            setSelectedFoodTruckName(item["foodTruckName"]);
            localStorage.setItem("selectedFoodTruckId", item["foodTruckId"]);
            break;
          }
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  const checkOnSale = () => {
    if (selectedFoodTruck === null) {
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
    // isValidUser 함수를 비동기로 호출하고 결과를 확인합니다.
    isValidUser();
  }, []);

  useEffect(() => {
    if (isValid) {
      updateFoodTruckList();
      checkOnSale();
    }
  }, [isValid]);

  useEffect(() => {
    updateFoodTruckList(true);
    checkOnSale();
  }, [selectedFoodTruck]);

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("grantType");
    localStorage.removeItem("selectedFoodTruckId");
    navigate("/login");
  };

  return (
    <MainPageLayout ref={mainRef} onClick={modalOutSideClick}>
      <div className="header">
        <LogoutP onClick={handleLogout}>로그아웃</LogoutP>
      </div>
      {loading ? (
        <Loading />
      ) : dataEmpty ? (
        <RegistFoodTruckButton />
      ) : (
        <>
          <FoodTruckSelector
            selectedFoodTruckName={selectedFoodTruckName}
            detailViewable={detailViewable}
            setDetailViewable={setDetailViewable}
          />
          {detailViewable && (
            <FoodTruckList
              foodTruckList={foodTruckList}
              setSelectedFoodTruck={setSelectedFoodTruck}
              setDetailViewable={setDetailViewable}
            />
          )}
          <VendorMainForm />
        </>
      )}
      <Navbar />
    </MainPageLayout>
  );
}

export default MainPage;
