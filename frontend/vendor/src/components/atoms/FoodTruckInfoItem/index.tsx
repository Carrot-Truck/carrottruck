import {InfoItemContainer} from './style';
import { useEffect} from 'react';

// FoodTruckInfo 컴포넌트에서 사용할 props 인터페이스
interface FoodTruckInfoProps {
  title: string;
  value: any; // 이 부분은 특정 데이터 구조에 따라 더 구체적인 타입을 지정할 수 있습니다.
}

const FoodTruckInfoItem = ({ title, value }: FoodTruckInfoProps) => {

  const renderValue = (value: any) => {
      if (value === null || value === '' || (typeof value === 'object' && Object.keys(value).length === 0)) {
        return "정보가 없습니다";
      }
      return value;
    };

  // const renderTime = (value: Array<any>) => {
  //   if(value === null ) return "정보가 없습니다.";
  //   return value.toString;
  // };
    useEffect(()=>{
      console.log("FoodTruckInfoItem ", title, value);
    }, [])

  const switchDateFormEngToKor = (value: string) => {
    switch(value){
      case 'MONDAY': return '월';
      case 'TUESDAY': return '화';
      case 'WEDNESDAY': return '수';
      case 'THURSDAY': return '목';
      case 'FRIDAY': return '금';
      case 'SATURDAY': return '토';
      case 'SUNDAY': return '일';
    }
  }    
  return (
    <InfoItemContainer>
      <h3>{title}</h3>
      {title === "영업정보" && (
        <>
          <div className="label">운영시간</div>
          <div className="value">
            {value.map((schedule: any, index: number) => (
              <div key={index}>
                {switchDateFormEngToKor(schedule.dayOfWeek)} : 
                {schedule.startTime} - {schedule.endTime} <br></br>
                {schedule.address}
              </div>
              
            ))}
          </div>

        </>
      )}
      {title === "사업자정보" && (
        <>
          <div className="label">대표자명</div>
          <div className="value">{renderValue(value.vendorName)}</div>
          <div className="label">상호명</div>
          <div className="value">{renderValue(value.tradeName)}</div>
          <div className="label">사업자번호</div>
          <div className="value">{renderValue(value.businessNumber)}</div>
        </>
      )}
      {title === "원산지 표기" && (
        <div className="full">{renderValue(value)}</div>
      )}
    </InfoItemContainer>
  );
};

export default FoodTruckInfoItem;
  