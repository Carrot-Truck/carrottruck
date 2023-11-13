import { MainPageLayout } from "./style";
import { useEffect, useState, useRef } from "react";
import Navbar from "components/organisms/Navbar";
import RegistFoodTruckButton from "components/organisms/RegistFoodTruckButton";
import VendorMainForm from "components/organisms/VendorMainForm";
import { AxiosResponse, AxiosError } from "axios";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getFoodTruckOverviews } from "api/foodtruck/foodTruck";
import FoodTruckSelector from "components/organisms/FoodTruckSelector";
import FoodTruckList from "components/organisms/FoodTruckList";

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
  const [detailViewable, setDetailViewable] = useState<boolean>(false);
  const [foodTruckList, setFoodTruckList] = useState<IFoodTruckListItem[]>([]);
  const [selectedFoodTruck, setSelectedFoodTruck] = useState<number | null>(null);
  const [selectedFoodTruckName, setSelectedFoodTruckName] = useState<string>("");

  const mainRef = useRef<HTMLDivElement>(null);

  const navigate = useNavigate();
  const accessToken = localStorage.getItem("accessToken");
  const grantType = localStorage.getItem("grantType");
  const APPLICATION_SPRING_SERVER_URL =
    process.env.NODE_ENV === "production"
      ? "https://k9c211.p.ssafy.io/api"
      : "http://localhost:8001/api";

  useEffect(() => {
    const isValidUser = async () => {
      try {
        const validToken = await axios.get(`${APPLICATION_SPRING_SERVER_URL}/member/vendor/info`, {
          headers: {
            Authorization: `${grantType} ${accessToken}`,
          },
        });
        console.log(validToken);
        return true;
      } catch (error) {
        const err = error as AxiosError;
        if (err.response?.status === 400 || err.response?.status === 401) {
          alert("로그인이 필요합니다.");
          return navigate("/login");
        } else {
          console.error("Error!!", error);
          alert("알 수 없는 오류가 발생하였습니다. \n다시 로그인 해주세요.");
          return navigate("/login"); // 리다이렉트하고 함수 종료
        }
      }
    };
    const hasVendorFoodTruck = async () => {
      //TODO: 하기 코드를 token 검증 API 나오면 그 API로 교체해야함.
      try {
        const response = await axios.get(
          `${APPLICATION_SPRING_SERVER_URL}/food-truck/overview?lastFoodTruckId=`,
          {
            headers: {
              Authorization: `${grantType} ${accessToken}`,
            },
          }
        );

        if (response.data.code === 200) {
          // 정보를 받았고,
          console.log(response.data.data.items);
          setDataEmpty(response.data.data.items.length === 0); // 그 정보가 비어있다면 true / 비어있지 않다면 false
        }
      } catch (error) {
        console.log("error", error);
        alert("처리 중 에러 발생!");
        navigate("/login");
      }
    };

    // isValidUser 함수를 비동기로 호출하고 결과를 확인합니다.
    isValidUser().then((isValid) => {
      if (isValid) {
        hasVendorFoodTruck();
      }
    });
  }, []);

  const modalOutSideClick = (e: any) => {
    if (mainRef.current === e.target) {
      setDetailViewable(false);
    }
  };

  const updateFoodTruckList = (isUpdate?: boolean) => {
    const requestParams: Object = {
      lastFoodTruckId: "",
    };

    getFoodTruckOverviews(
      requestParams,
      (response: AxiosResponse) => {
        const data = getData(response);
        setFoodTruckList(data["items"]);

        for (const item of data["items"]) {
          if (item["selected"]) {
            if (!isUpdate) setSelectedFoodTruck(item["foodTruckId"]);
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

  useEffect(() => {
    updateFoodTruckList(true);
  }, []);

  useEffect(() => {
    updateFoodTruckList();
  }, [selectedFoodTruck]);

  return (
    <MainPageLayout ref={mainRef} onClick={modalOutSideClick}>
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
      {dataEmpty ? ( // 데이터가 비어 있으면 RegistFoodTruckButton을 표시합니다.
        <RegistFoodTruckButton />
      ) : (
        // 데이터가 비어 있지 않으면 VendorForm을 표시합니다.
        <VendorMainForm />
      )}
      <Navbar></Navbar>
    </MainPageLayout>
  );
}

export default MainPage;
