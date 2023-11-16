import NaverMap from "components/atoms/Map";
import { AnalysisResponseContainer } from "./style";
import TitleText from "components/atoms/TitleText";

interface IAnalysisResponseProps {
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

interface ILocation {
  latitude: number;
  longitude: number;
}

function AnalysisResponse({
  categoryName,
  sido,
  sigungu,
  dong,
  radiusCount,
  addressCount,
  stores,
}: IAnalysisResponseProps) {
  const clientId: string = process.env.REACT_APP_CLIENT_ID || "";

  const locations: ILocation[] = stores.map((store) => ({
    latitude: store.latitude,
    longitude: store.longitude,
  }));

  const address: string = `${sido} ${sigungu} ${dong}`;

  return (
    <AnalysisResponseContainer>
      <NaverMap clientId={clientId} markers={locations} dynamicheight="60vh" />
      <div className="analysis-info">
        <TitleText text={categoryName} size={"s"} textAlign={"left"} />
        <p>{`${address}`}</p>
      </div>
      <div className="analysis-result">
        <p>{`반경 1km 내 검색결과: { ${radiusCount} } 건`}</p>
        <p>{`${dong} 내 검색결과: { ${addressCount} } 건`}</p>
      </div>
    </AnalysisResponseContainer>
  );
}

export default AnalysisResponse;
