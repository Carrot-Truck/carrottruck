import { useState } from 'react';
import { FoodTruckLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import Button from 'components/atoms/Button';

import Pocha from 'assets/imgs/image 21.png';
import ModifyButton from 'assets/icons/modify_icon.svg';
import EmptyHeart from 'assets/icons/empty_heart.svg';
import Pin from 'assets/icons/pin.svg';
import Star from 'assets/icons/star.svg';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';

function FoodTruckPage() {
  const foodTruck = {
    foodTruckDetail: {
      foodTruckId: 1,
      foodTruckName: '동현 된장삼겹',
      phoneNumber: '010-1234-5678',
      content: '된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭',
      originInfo: '돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)',
      isOpen: true,
      isLiked: true,
      prepareTime: 30,
      grade: 4.5,
      reviewCount: 1324,
      distance: 123,
      address: '광주 광산구 장덕로 5번길 16',
      foodTruckImageId: 1,
      isNew: true,
      vendorName: '김동현',
      tradeName: '동현 된장삼겹',
      businessNumber: '123-45-23523'
    },
    menus: [
      {
        menuId: 1,
        menuName: '달콤짭짤한 밥도둑 된장 삼겹살 구이',
        menuPrice: 8900,
        menuDescription: '동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!',
        menuSoldOut: false,
        menuImageId: 1
      },
      {
        menuId: 2,
        menuName: '노른자 된장 삼겹살 덮밥',
        menuPrice: 6900,
        menuDescription: '감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥',
        menuSoldOut: false,
        menuImageId: 2
      }
    ],
    schedules: [
      {
        scheduleId: 1,
        address: '광주 광산구 장덕로5번길 16',
        days: '월요일',
        startTime: '17:00',
        endTime: '01:00'
      },
      {
        scheduleId: 2,
        address: '광주 광산구 장덕로5번길 16',
        days: '화요일',
        startTime: '17:00',
        endTime: '01:00'
      },
      {
        scheduleId: 3,
        address: '',
        days: '수요일',
        startTime: '',
        endTime: ''
      }
    ],
    reviews: [
      {
        reviewId: 1,
        nickname: '아닌데?소대장',
        grade: 4,
        content: '정말 맛있게 먹었어요'
      },
      {
        reviewId: 2,
        nickname: '어서와',
        grade: 5,
        content: '안주로 최고에요!! 너무 맛있어서 숙취에 시달릴만큼 많이 마셨어요'
      }
    ]
  };
  const [selectedButton, setSelectedButton] = useState(1);

  const buttonClick = (buttonNumber: number) => {
    setSelectedButton(buttonNumber);
  };

  return (
    <FoodTruckLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <img src={ModifyButton} alt="" />
      </div>
      <img className="headerImage" src={Pocha} alt="" />
      <div className="storeInfo">
        <div className="foodTruckName">
          <span>{foodTruck.foodTruckDetail.foodTruckName}</span>
          <img src={EmptyHeart} alt="" />
        </div>
        <div className="location">
          <span>{foodTruck.foodTruckDetail.address} </span>

          <span> {foodTruck.foodTruckDetail.distance}m </span>
          <img src={Pin} alt="" />
        </div>
        <div className="review">
          <div>
            <img src={Star} alt="" />
            <span>({foodTruck.foodTruckDetail.grade})</span>
            <span>{foodTruck.foodTruckDetail.reviewCount}</span>
          </div>
          {foodTruck.foodTruckDetail.isOpen ? <span id="open">open</span> : <span id="close">close</span>}
        </div>
      </div>
      <div className="switchButton">
        <Button
          size="m"
          radius="l"
          color={selectedButton === 1 ? 'Primary' : 'SubFirst'}
          text="메뉴"
          handleClick={() => buttonClick(1)}
        />
        <Button
          size="m"
          radius="l"
          color={selectedButton === 2 ? 'Primary' : 'SubFirst'}
          text="가게정보"
          handleClick={() => buttonClick(2)}
        />
        <Button
          size="m"
          radius="l"
          color={selectedButton === 3 ? 'Primary' : 'SubFirst'}
          text="리뷰"
          handleClick={() => buttonClick(3)}
        />
      </div>
      {selectedButton === 1 && <FoodTruckMenu menus={foodTruck.menus}></FoodTruckMenu>}
      {/* {selectedButton === 2 && } */}
      {/* {selectedButton === 3 && } */}
    </FoodTruckLayout>
  );
}

export default FoodTruckPage;
