// import React from 'react';
// import { useLocation } from 'react-router-dom';
import { FoodTruckInfoContainer } from './style';
import FoodTruckInfoItem from "../../atoms/FoodTruckInfoItem"
import Button from 'components/atoms/Button';

// 메뉴 아이템 인터페이스를 정의합니다.
interface FoodTruck {
  foodTruckDetail: {
    foodTruckId : number,
    foodTruckName : string,
    phoneNumber : string,
    content : string,
    originInfo : string,
    isOpen : boolean,
    isLiked : boolean,
    prepareTime : number,
    avgGrade : number,
    likeCount : number,
    reviewCount : number,
    distance : number,
    address : string,
    foodTruckImageUrl : string,
    selected : boolean,
    isNew : boolean,
    vendorName : string,
    tradeName : string,
    businessNumber : string
  };
  // menus: Array<{
  //     menuId: 0,
  //     menuName: '',
  //     menuPrice: 0,
  //     menuDescription: '',
  //     menuSoldOut: false,
  //     menuImageUrl: ''
  // }>;
  schedules: Array<{
    scheduleId: number,
    address: string,
    dayOfWeek: string,
    startTime: string,
    endTime: string
  }>;

  // reviews: Array<{
  //   reviewId: 0,
  //   nickname: '',
  //   grade: 0,
  //   content: '',
  //   imageUrl: ''
  // }>;
};
const modifyInfo = ()=>{
  
}

function FoodTruckInfo({ foodTruckDetail, schedules }: FoodTruck) {
  return (
    <FoodTruckInfoContainer>
      <FoodTruckInfoItem title="영업정보" value={schedules}></FoodTruckInfoItem>
      <FoodTruckInfoItem title="사업자정보"  value={foodTruckDetail}></FoodTruckInfoItem>
      <FoodTruckInfoItem title="원산지 표기" value={foodTruckDetail.originInfo}></FoodTruckInfoItem>
      <Button size="l" radius='m' color='Primary' handleClick={modifyInfo} text="스케줄 수정"/>
    </FoodTruckInfoContainer>
  );
}

export default FoodTruckInfo;
