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
  savedMarker: any;
}

const ScheduleMap: React.FC<INaverMapProps> = ({ clientId, savedMarker, onMarkerChange }) => {
  const mapRef = useRef<HTMLDivElement>(null);
  const markerRef = useRef(null);

  useEffect(() => {
    // 지도 로딩 및 초기화 로직
    const loadMap = () => {
      const initialCenter = new window.naver.maps.LatLng(savedMarker.latitude, savedMarker.longitude);
      const mapOptions = {
        center: initialCenter,
        zoom: 17
      };

      const map = new window.naver.maps.Map(mapRef.current, mapOptions);
      const marker = new window.naver.maps.Marker({
        position: initialCenter,
        map
      });
      markerRef.current = marker;

      // 지도 중심이 변경될 때마다 마커 위치 업데이트
      window.naver.maps.Event.addListener(map, 'center_changed', () => {
        const center = map.getCenter();
        marker.setPosition(center);
        onMarkerChange(center.lat(), center.lng());
      });
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
  }, [clientId, savedMarker]);

  return <MapWrapper ref={mapRef} />;
};

export default ScheduleMap;

