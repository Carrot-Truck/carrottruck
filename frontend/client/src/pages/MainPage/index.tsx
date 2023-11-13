import { useState, useRef } from 'react';
import { MainPageLayout, FoodTruckListLayout } from './style';
import NaverMap from 'components/atoms/Map';
import FoodTruckList from 'components/organisms/FoodTruckList';
import Navbar from 'components/organisms/Navbar';
import useBottomSheet from 'hooks/useBottomSheet';

function MainPage() {
  interface Marker {
    latitude: number;
    longitude: number;
  }

  const CLIENT_KEY: string = process.env.REACT_APP_CLIENT_ID || 'your-default-key-or-handle-error';

  // 초기 높이를 25vh로 설정, 컴포넌트의 참조를 저장하기 위한 ref
  const { sheet, content } = useBottomSheet();

  // 푸드트럭 지도 위치(마커)조회
  const truckData = {
    markerCount: 2,
    markerItems: [
      {
        categoryId: 1,
        foodTruckId: 1,
        latitude: '37.5665',
        longitude: '126.978',
        distance: '100',
        isOpen: true
      },
      {
        categoryId: 2,
        foodTruckId: 2,
        latitude: '35.1595',
        longitude: '126.8526',
        distance: '100',
        isOpen: false
      }
    ]
  };

  const foodTruckList = {
    hasNext: false,
    items: [
      {
        foodTruckScheduleId: 1,
        categoryId: 1,
        foodTruckId: 1,
        foodTruckName: '동현 된장삼겹',
        isOpen: false,
        isLiked: true,
        prepareTime: 30,
        likeCount: 143,
        grade: 4.5,
        reviewCount: 1324,
        distance: 123,
        address: '광주 광산구 장덕로 5번길 16',
        foodTruckImageUrl: 'imageUrl',
        isNew: true
      },
      {
        foodTruckScheduleId: 2,
        categoryId: 2,
        foodTruckId: 2,
        foodTruckName: '팔천순대',
        isOpen: true,
        isLiked: false,
        prepareTime: 20,
        likeCount: 132,
        grade: 4.0,
        reviewCount: 1324,
        distance: 100,
        address: '수완자이아파트정문',
        foodTruckImageUrl: 'imageUrl',
        isNew: false
      }
    ]
  };

  //truckData에서 위도 경도를 추출하여 string에서 number로 변환
  const extractMarkers = (truckData: { markerItems: Array<{ latitude: string; longitude: string }> }): Array<Marker> =>
    truckData.markerItems.map((item) => ({
      latitude: parseFloat(item.latitude),
      longitude: parseFloat(item.longitude)
    }));

  const markers = extractMarkers(truckData);

  return (
    <MainPageLayout>
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
