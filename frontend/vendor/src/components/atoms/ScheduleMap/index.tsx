import React, { useEffect, useRef } from 'react';
import { MapWrapper } from './style';

// 네이버 지도 타입 선언 (네이버 지도 라이브러리 타입 정의 필요)
declare global {
  interface Window {
    naver: any;
  }
}

interface INaverMapProps {
  clientId: string; // 네이버 클라우드 플랫폼에서 발급받은 Client ID
  onMarkerChange: (latitude: number, longitude: number) => void;
}
const ScheduleMap: React.FC<INaverMapProps> = ({ clientId, onMarkerChange  }) => {
  const mapRef = useRef<HTMLDivElement>(null);
  const markerRef = useRef(null); // 마커를 참조하기 위한 ref

  const updateLocation = (currentLocation: any) =>{

    const initialCenter = new window.naver.maps.LatLng(currentLocation.latitude, currentLocation.longitude); // 초기 중심 좌표 (예: 서울 시청)

    const mapOptions = {
      center: initialCenter,
      zoom: 17
    };

    const map = new window.naver.maps.Map(mapRef.current, mapOptions);

    // 마커 생성
    const marker = new window.naver.maps.Marker({
      position: initialCenter,
      map
    });
    markerRef.current = marker; // 마커 참조 저장

    // 지도 중심이 변경될 때마다 마커 위치 업데이트
    window.naver.maps.Event.addListener(map, 'center_changed', () => {
      const center = map.getCenter();
      marker.setPosition(center);
      onMarkerChange(center.lat(), center.lng()); // 콜백 함수 호출
    });

  }
  useEffect(() => {
    const loadMap = () => {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const currentLocation = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
          };
          updateLocation(currentLocation);
        },
        (error) => {
          console.error('Geolocation Error:', error);
          const currentLocation = {
            latitude: 37.5665,
            longitude: 126.9780
          };
          updateLocation(currentLocation);
        }
      );
    };

    // 네이버 지도 스크립트 로드
    const script = document.createElement('script');
    script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}&submodules=geocoder`;
    script.async = true;
    script.onload = loadMap;
    document.head.appendChild(script);

    return () => {
      document.head.removeChild(script);
    };
  }, [clientId]);

  return <MapWrapper ref={mapRef} />;
};

export default ScheduleMap;
