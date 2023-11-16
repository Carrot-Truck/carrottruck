import React, { useEffect, useRef, useState } from "react";
import { MapWrapper } from "./style";

declare global {
  interface Window {
    naver: any;
  }
}

interface INaverMapProps {
  clientId: string;
  marker: { latitude: number; longitude: number };
  dynamicheight?: string;
}

const SalesMap: React.FC<INaverMapProps> = ({ clientId, marker, dynamicheight }) => {
  const [isScriptLoaded, setScriptLoaded] = useState<boolean>(false);

  const mapRef = useRef<HTMLDivElement>(null);

  const loadMap = (latitude: number, longitude: number) => {
    const mapOptions = {
      center: new window.naver.maps.LatLng(latitude, longitude),
      zoom: 15,
    };

    const map = new window.naver.maps.Map(mapRef.current, mapOptions);

    new window.naver.maps.Marker({
      position: new window.naver.maps.LatLng(latitude, longitude),
      map,
    });
  };

  useEffect(() => {
    // 네이버 지도 스크립트를 동적으로 로드
    const script = document.createElement("script");
    script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}&submodules=geocoder`;
    script.async = true;
    script.onload = () => setScriptLoaded(true);
    document.head.appendChild(script);

    return () => {
      // 컴포넌트 언마운트 시 스크립트 제거
      document.head.removeChild(script);
    };
  }, [clientId]);

  useEffect(() => {
    if (isScriptLoaded) {
      loadMap(marker.latitude, marker.longitude);
    }
  }, [isScriptLoaded, marker.latitude, marker.longitude]);

  return <MapWrapper ref={mapRef} $dynamicheight={dynamicheight} />;
};

export default SalesMap;
