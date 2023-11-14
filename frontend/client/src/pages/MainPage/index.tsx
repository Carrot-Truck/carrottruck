import { useEffect, useState } from 'react';
import { MainPageLayout, FoodTruckListLayout } from './style';
import { getFoodTruckMarkers, getSearchedFoodTrucks } from 'api/foodtruck/foodTruck';
import NaverMap from 'components/atoms/Map';
import FoodTruckList from 'components/organisms/FoodTruckList';
import Navbar from 'components/organisms/Navbar';
import useBottomSheet from 'hooks/useBottomSheet';
import ShoppingCartItem from 'components/atoms/ShoppingCartItem';

function MainPage() {
    interface Marker {
    categoryId: number,
    latitude: number;
    longitude: number;
  }

    const [truckData, setTruckData] = useState({
        markerCount: 0,
        markerItems: []
    });
    const [markers, setMarkers] = useState<Array<Marker>>([]);
    const [foodTruckList, setFoodTruckList] = useState({
        hasNext: Boolean,
        items: []
    });

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
        }

        getCurrentLocation();

    }, []);


    const fetchFoodTruckMarkers = (latitude: number, longitude: number) => {
        getFoodTruckMarkers({
                latitude, longitude, showAll: true
            },
            (response: any) => {
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
        getSearchedFoodTrucks({
            latitude, longitude, showAll: true
        },
            (response: any) => {
                setFoodTruckList(response.data.data);
                if (response.data.data.items.length > 0) {
                    setFoodTruckList(response.data.data);
                }
            },
            (error: any) => {
                console.error('API Error: ', error);
            }
        )
    }

//   truckData에서 위도 경도를 추출하여 string에서 number로 변환
    const extractMarkers = (data: { markerItems: Array<{ categoryId: number; latitude: string; longitude: string }> }): Array<Marker> =>
      data.markerItems.map((item) => ({
        categoryId: item.categoryId,
      latitude: parseFloat(item.latitude),
      longitude: parseFloat(item.longitude)
    }));

  return (
    <MainPageLayout>
      <ShoppingCartItem></ShoppingCartItem>
      <NaverMap clientId={CLIENT_KEY} markers={markers}></NaverMap>
      <FoodTruckListLayout ref={sheet}>
        <div className="header">
          <div className="handle"></div>
        </div>
        <div className="bottomsheetcontent" ref={content}>
          <FoodTruckList foodTrucks={foodTruckList.items}></FoodTruckList>
        </div>
      </FoodTruckListLayout>
      <Navbar></Navbar>
    </MainPageLayout>
  );
}

export default MainPage;
