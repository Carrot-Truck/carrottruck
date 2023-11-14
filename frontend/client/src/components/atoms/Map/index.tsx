import React, { useEffect, useState, useRef } from 'react';
import { MapWrapper } from './style';

// 네이버 지도 타입 선언 (네이버 지도 라이브러리 타입 정의 필요)
declare global {
  interface Window {
    naver: any;
  }
}

interface INaverMapProps {
    clientId: string; // 네이버 클라우드 플랫폼에서 발급받은 Client ID
    markers: Array<{ categoryId: number; foodTruckId: number; latitude: number; longitude: number; }>;
    onMarkerClick: (foodTruckId: number, latitude: number, longitude: number) => void;
    onMapClick: (latitude: number, longitude: number) => void;
}

    const NaverMap: React.FC<INaverMapProps> = ({ clientId, markers, onMarkerClick, onMapClick }) => {
    const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // 사용자의 현재 위치를 가져오는 함수
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            loadMap(latitude, longitude);
          },
          (error) => {
            console.error('Geolocation service failed', error);
          }
        );
      } else {
        console.error('Geolocation is not supported by this browser.');
      }
    };

    // 네이버 지도를 로드하는 함수
    const loadMap = (latitude: number, longitude: number) => {
      const mapOptions = {
        center: new window.naver.maps.LatLng(latitude, longitude),
        zoom: 16
      };

        const map = new window.naver.maps.Map(mapRef.current, mapOptions);

      // 사용자 현재 위치에 마커를 찍습니다.
      new window.naver.maps.Marker({
        position: new window.naver.maps.LatLng(latitude, longitude),
        map: map
      });

        console.log(markers);
        let activeMarker: any | null = null; // 임시 변수 선언
      // 백엔드에서 받은 위치 정보로 마커를 찍습니다.
        markers.forEach((marker) => {
        const imagePath = require(`assets/icons/category${marker.categoryId}.svg`);
        const naverMarker = new window.naver.maps.Marker({
          position: new window.naver.maps.LatLng(marker.latitude, marker.longitude),
            map: map,
            // icon: imagePath
            icon: {
                url: imagePath,
                scaledSize: new window.naver.maps.Size(50, 35),
                origin: new window.naver.maps.Point(0, 0),
                anchor: new window.naver.maps.Point(25, 35)
            }
        });
            
        window.naver.maps.Event.addListener(map, 'click', () => {
            onMapClick(latitude, longitude);
            if (activeMarker) {
                activeMarker.setAnimation(null);
                activeMarker = null;
            }
        });
            
        window.naver.maps.Event.addListener(naverMarker, 'click', () => {
            onMarkerClick(marker.foodTruckId, latitude, longitude);
            map.setCenter(new window.naver.maps.LatLng(marker.latitude, marker.longitude));

            console.log(activeMarker == null);
            if (activeMarker) {
                activeMarker.setAnimation(null);
            }

            naverMarker.setAnimation(1);
            activeMarker = naverMarker; // 임시 변수 업데이트
        });
            
      });
    };

    // 네이버 지도 스크립트를 동적으로 로드
    const script = document.createElement('script');
    script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}&submodules=geocoder`;
    script.async = true;
    script.onload = () => getCurrentLocation();
      document.head.appendChild(script);

    return () => {
      // 컴포넌트 언마운트 시 스크립트 제거
      document.head.removeChild(script);
    };
  }, [clientId, markers]);

  return <MapWrapper ref={mapRef} />;
};

export default NaverMap;
