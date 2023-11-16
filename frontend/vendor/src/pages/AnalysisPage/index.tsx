import Navbar from "components/organisms/Navbar";
import { AnalysisLayout } from "./style";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router";
import NaverMap from "components/atoms/Map";
import { useState, useEffect } from "react";
import CategoryList from "components/organisms/CategoryList";
import { AxiosResponse } from "axios";
import { getCategories } from "api/foodtruck/category";
import { getStoreAnalysis } from "api/analysis";
import Loading from "components/atoms/Loading";
import TitleText from "components/atoms/TitleText";
import AnalysisResult from "components/organisms/AnalysisResult";

interface ICategory {
  categoryId: number;
  categoryName: string;
}

interface ICoords {
  latitude: number;
  longitude: number;
}

interface IAnalysisResponse {
  categoryName: string;
  sido: string;
  sigungu: string;
  dong: string;
  radiusCount: number;
  addressCount: number;
  stores: IStoreResponse[];
}

interface IStoreResponse {
  storeName: string;
  latitude: number;
  longitude: number;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function AnalysisPage() {
  const [showCategory, setShowCategory] = useState<boolean>(false);
  const [categories, setCategory] = useState<ICategory[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<number | null>(null);
  const [coords, setCoords] = useState<ICoords | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [analysisData, setAnalsysisData] = useState<IAnalysisResponse | null>(null);

  const navigate = useNavigate();

  const clientId: string = process.env.REACT_APP_CLIENT_ID || "";

  const fetchData = async () => {
    getCategories(
      (response: AxiosResponse) => {
        const data = getData(response);
        setCategory(data["categories"]);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

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

  useEffect(() => {
    fetchData();
    getCurrentLocation();
  }, []);

  const getAnalysisData = (retry: number) => {
    if (selectedCategory === null) {
      alert("카테고리를 선택해주세요.");
      setLoading(false);
      return;
    }

    const requestQuery: Record<string, any> = {
      latitude: coords?.latitude,
      longitude: coords?.longitude,
    };

    getStoreAnalysis(
      selectedCategory,
      requestQuery,
      (response: AxiosResponse) => {
        const data = getData(response);
        setAnalsysisData(data);
        setLoading(false);
      },
      (error: any) => {
        if (retry < 5) {
          console.log(`불러오지 못했어요.. 다시 시도할게요 ${retry + 1}/5`);
          getAnalysisData(retry + 1);
        } else {
          console.log(error);
          alert("잠시후에 다시 시도해주세요.");
          setLoading(false);
          setSelectedCategory(null);
        }
      }
    );
  };

  const handleCategoryClick = () => {
    setLoading(true);
    getAnalysisData(0);
  };

  return (
    <AnalysisLayout>
      {loading ? (
        <>
          <TitleText text={"상권 분석중..."} size={"m"} textAlign={"center"} />
          <Loading />
        </>
      ) : analysisData !== null ? (
        <>
          <AnalysisResult
            categoryName={analysisData.categoryName}
            sido={analysisData.sido}
            sigungu={analysisData.sigungu}
            dong={analysisData.dong}
            radiusCount={analysisData.radiusCount}
            addressCount={analysisData.addressCount}
            stores={analysisData.stores}
          />
        </>
      ) : showCategory ? (
        <>
          <CategoryList
            categories={categories}
            onCategoryClick={setSelectedCategory}
            selectedCategoryId={selectedCategory}
          />
          <Button
            size="m"
            radius="m"
            color="Primary"
            text="상권 분석"
            handleClick={handleCategoryClick}
          />
        </>
      ) : (
        <>
          <NaverMap clientId={clientId} markers={[]} dynamicheight={"70vh"} />
          <div className="button-wrapper">
            <Button
              size="m"
              radius="m"
              color="Primary"
              text="업종 선택"
              handleClick={() => {
                setShowCategory(true);
              }}
            />
          </div>
          <div className="button-wrapper">
            <Button
              size="m"
              radius="m"
              color="Primary"
              text="수요조사"
              handleClick={() => {
                navigate("/survey");
              }}
            />
          </div>
        </>
      )}
      <Navbar />
    </AnalysisLayout>
  );
}

export default AnalysisPage;
