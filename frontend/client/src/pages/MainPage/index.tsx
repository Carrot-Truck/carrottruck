import { useEffect, useState } from 'react';
import { MainPageLayout, FoodTruckListLayout } from './style';
import { getFoodTruckMarkers, getSearchedFoodTrucks } from 'api/foodtruck/foodTruck';
import NaverMap from 'components/atoms/Map';
import FoodTruckList from 'components/organisms/FoodTruckList';
import Navbar from 'components/organisms/Navbar';
import useBottomSheet from 'hooks/useBottomSheet';
import ShoppingCartItem from 'components/atoms/ShoppingCartItem';
import { getFoodTruck } from 'api/foodtruck/foodTruck';
import Toggle from 'components/atoms/Toggle';

function MainPage() {
  interface Marker {
    categoryId: number;
    foodTruckId: number;
    foodTruckName: string;
    latitude: number;
    longitude: number;
  }

  const [truckData, setTruckData] = useState({ markerCount: 0, markerItems: [] });
  const [markers, setMarkers] = useState<Array<Marker>>([]);
  const [foodTruckList, setFoodTruckList] = useState<{ hasNext: boolean; items: any[] }>({ hasNext: false, items: [] });

  const CLIENT_KEY: string = process.env.REACT_APP_CLIENT_ID || 'your-default-key-or-handle-error';

  // 초기 높이를 25vh로 설정, 컴포넌트의 참조를 저장하기 위한 ref
  const { sheet, content } = useBottomSheet();

  // 푸드트럭 지도 위치(마커)조회
  useEffect(() => {
    // 현재 위치를 가져오는 함수
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            fetchFoodTruckMarkers(latitude, longitude);
            fetchFoodTruckList(latitude, longitude);
          },
          (error) => {
            console.error('Geolocation Error:', error);
          }
        );
      } else {
        alert('Geolocation is not supported by this browser.');
      }
    };

    getCurrentLocation();
  }, []);

  const fetchFoodTruckMarkers = (latitude: number, longitude: number) => {
    getFoodTruckMarkers(
      {
        latitude,
        longitude,
        showAll: true
      },
      (response: any) => {
        console.log(response.data.data);
        setTruckData(response.data.data); // 응답 데이터를 상태에 설정
        if (response.data.data.markerItems.length > 0) {
          const newMarkers = extractMarkers(response.data.data);
          setMarkers(newMarkers);
        }
      },
      (error: any) => {
        console.error('API Error: ', error);
      }
    );
  };

  const fetchFoodTruckList = (latitude: number, longitude: number) => {
    getSearchedFoodTrucks(
      {
        latitude,
        longitude,
        showAll: true
      },
      (response: any) => {
        console.log(response.data.data);
        setFoodTruckList(response.data.data);
        if (response.data.data.items.length > 0) {
          setFoodTruckList(response.data.data);
        }
      },
      (error: any) => {
        console.error('API Error: ', error);
      }
    );
  };

  // truckData에서 위도 경도를 추출하여 string에서 number로 변환
  const extractMarkers = (data: {
    markerItems: Array<{
      categoryId: number;
      foodTruckId: number;
      foodTruckName: string;
      latitude: string;
      longitude: string;
    }>;
  }): Array<Marker> =>
    data.markerItems.map((item) => ({
      categoryId: item.categoryId,
      foodTruckId: item.foodTruckId,
      foodTruckName: item.foodTruckName,
      latitude: parseFloat(item.latitude),
      longitude: parseFloat(item.longitude)
    }));

  const handleMapClick = (latitude: number, longitude: number) => {
    getSearchedFoodTrucks(
      {
        latitude,
        longitude,
        showAll: true
      },
      (response: any) => {
        setFoodTruckList(response.data.data);
      },
      (error: any) => {
        console.error('API Error: ', error);
      }
    );
  };

  // map MarkerClick event 처리
  const handleMarkerClick = (foodTruckId: number, foodTruckName: string, latitude: number, longitude: number) => {
    getFoodTruck(
      foodTruckId,
      {
        latitude: latitude,
        longitude: longitude
      },
      (response: any) => {
        // 성공 시의 처리 로직
        console.log('Success:', response.data.data);

        setFoodTruckList(() => {
          const selectedTruck = {
            categoryId: response.data.data.foodTruck.categoryId, // 예시, categoryId가 응답에 포함되어 있다고 가정
            foodTruckId: response.data.data.foodTruck.foodTruckId,
            foodTruckImageUrl: response.data.data.foodTruck.foodTruckImageUrl,
            foodTruckName: response.data.data.foodTruck.foodTruckName,
            grade: response.data.data.foodTruck.avgGrade, // 평균 점수를 grade로 가정
            isLiked: response.data.data.foodTruck.isLiked,
            isNew: response.data.data.foodTruck.isNew,
            isOpen: response.data.data.foodTruck.isOpen,
            likeCount: response.data.data.foodTruck.likeCount,
            reviewCount: response.data.data.foodTruck.reviewCount
          };

          return {
            hasNext: false,
            items: [selectedTruck]
          };
        });

        // 필요한 경우, setFoodTruckList 등의 상태 업데이트 로직 추가
      },
      (error: any) => {
        // 실패 시의 처리 로직
        console.error('Error:', error);
      }
    );
  };

  const toggleLike = (foodTruckId: number) => {
    setFoodTruckList((prevState) => {
      return {
        ...prevState,
        items: prevState.items.map((item) =>
          item.foodTruckId === foodTruckId ? { ...item, isLiked: !item.isLiked } : item
        )
      };
    });
  };

  return (
    <MainPageLayout>
      <div className="toggle">
        <Toggle></Toggle>
      </div>
      <NaverMap
        clientId={CLIENT_KEY}
        markers={markers}
        foodTruckList={foodTruckList}
        onMarkerClick={handleMarkerClick}
        onMapClick={handleMapClick}
      ></NaverMap>
      <FoodTruckListLayout ref={sheet}>
        <div className="header">
          <div className="handle"></div>
        </div>
        <div className="bottomsheetcontent" ref={content}>
          {foodTruckList.items.length === 0 ? (
            <span>근처에 푸드트럭이 없습니다.</span>
          ) : (
            <FoodTruckList foodTrucks={foodTruckList.items} onToggleLike={toggleLike}></FoodTruckList>
          )}
        </div>
      </FoodTruckListLayout>
      <Navbar></Navbar>
    </MainPageLayout>
  );
}

export default MainPage;
