import { useState, useEffect } from 'react';
import { FoodTruckLayout } from './style';
import Navbar from "components/organisms/Navbar";
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
// import Pocha from 'assets/imgs/image 21.png';
import ModifyButton from 'assets/icons/modify_icon.svg';
// import EmptyHeart from 'assets/icons/empty_heart.svg';
// import Pin from 'assets/icons/pin.svg';
import Star from 'assets/icons/star.svg';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';
import {getFoodTruckOverviews} from 'api/foodtruck/foodTruck';
import {getFoodTruckReview} from 'api/review'
import {getFoodTruckDetails} from 'api/foodtruck/foodTruck'
import { AxiosResponse, AxiosError } from 'axios';
import { getSchedules } from 'api/schedule';
import BackSpaceHome from 'components/atoms/BackHome';
import FoodTruckInfo from 'components/organisms/FoodTruckInfo';
import FoodTruckReview from 'components/organisms/FoodTruckReview';

function FoodTruckPage() {
  const navigate = useNavigate();
  const [selectedButton, setSelectedButton] = useState(1);
    // ( 현재 선택된 푸드트럭 id 가져와야해 ) -> 우선순위 보류
    
  const navigateToModifyPage = () => {
      navigate('/foodtruck/modify', { state: { foodTruck: foodTruck } });
      // console.log(foodTruck);
    };
    
  const buttonClick = (buttonNumber: number) => {
    setSelectedButton(buttonNumber);
  };
  const [foodTruck, setFoodTruck] = useState({
    foodTruck: {
      foodTruckId: 0,
      foodTruckName: '',
      phoneNumber: '',
      content: '',
      originInfo: '',
      isOpen: false,
      isLiked: false,
      prepareTime: 30,
      avgGrade: 4.5,
      likeCount: 132,
      reviewCount: 1324,
      distance: 123.123,
      address: '',
      foodTruckImageUrl:
        'https://carrottruck.s3.ap-northeast-2.amazonaws.com/%EB%8B%B9%EA%B7%BC%ED%8A%B8%EB%9F%AD%EB%A1%9C%EA%B3%A0.png',
      selected: true,
      isNew: true,
      vendorName: '',
      tradeName: '',
      businessNumber: ''
    },
    menus: [
      {
        menuId: 0,
        menuName: '',
        menuPrice: 0,
        menuDescription: '',
        menuSoldOut: false,
        menuImageUrl: ''
      }
    ],
    schedules: [
      {
        scheduleId: 0,
        address: '',
        dayOfWeek: '',
        startTime: '',
        endTime: ''
      }
    ],
    reviews: [
      {
        reviewId: 0,
        nickname: '',
        grade: 0,
        content: '',
        imageUrl: ''
      }
    ]
  });

  const afterUpdateSchedule = (response: AxiosResponse) => {
    // console.log(response);

    setFoodTruck((prevState) => {
      if (prevState.schedules !== response.data.data.schedules) {
        return {
          ...prevState,
          schedules: response.data.data.schedules
        };
      } else {
        return prevState;
      }
    });
  };

  const afterUpdateMenu = (response: AxiosResponse) => {
    // console.log('Menu data fetched successfully', response.data.data);
    // const menusData = response.data.data.menus;
    // const newFoodTruck = response.data.data.foodTruck;
        
    setFoodTruck((prevState) => {
      if (prevState.foodTruck !== response.data.data.foodTruck || prevState.menus !== response.data.data.menus) {
        return {
          ...prevState,
          foodTruck: response.data.data.foodTruck,
          menus: response.data.data.menus
        };
      } else {
        return prevState;
      }
    });
    const foodTruckId = {
      foodTruckId: response.data.data.foodTruck.foodTruckId
    };
    getSchedules(foodTruckId, afterUpdateSchedule, handleFail);
  }

  const afterUpdateReview = (response: AxiosResponse) => {
    // console.log('Review data fetched successfully', response.data.data);
    const averageGrade = response.data.data.averageGrade;
    const foodTruckReviewDtoList = response.data.data.foodTruckReviewDtoList;
    
    setFoodTruck(prevState =>{
      // 현재 상태와 비교하여 값이 다를 때만 업데이트
      if (prevState.foodTruck.avgGrade !== averageGrade || prevState.reviews !== foodTruckReviewDtoList) {
        return {
          ...prevState,
          grade: averageGrade,
          reviews: foodTruckReviewDtoList
        };
      } else {
        return prevState; // 상태가 변하지 않으면 이전 상태를 반환하여 업데이트 방지
      }
    });

  }
  
  const updateFoodTruckId = (response: AxiosResponse) => {
    // console.log(response);
    const newFoodTruckId = response.data.data.items[response.data.data.items.length-1].foodTruckId;
    const newFoodTruckName = response.data.data.items[response.data.data.items.length-1].foodTruckName;
    
    setFoodTruck(prevState => {
      // 현재 상태와 비교하여 값이 다를 때만 업데이트
      if (prevState.foodTruck.foodTruckId !== newFoodTruckId || prevState.foodTruck.foodTruckName !== newFoodTruckName) {
        return {
          ...prevState,
          foodTruckId: newFoodTruckId,
          foodTruckName: newFoodTruckName
        };
      } else {
        return prevState; // 상태가 변하지 않으면 이전 상태를 반환하여 업데이트 방지
      }
    });
    getFoodTruckDetails(newFoodTruckId, afterUpdateMenu, handleFail);
    getFoodTruckReview(newFoodTruckId, afterUpdateReview, handleFail);
  };

  // handleFail 함수 수정
  const handleFail = (error: AxiosError) => {
    console.error('Error fetching data', error);
    alert('등록된 푸트드럭이 없습니다.\n먼저 푸드트럭을 등록해주세요.');
    navigate('/');
  };

  const lastFoodTruckId = {
    lastFoodTruckId: ''
  };

  useEffect(() => {
    // 내 푸트드럭 ID 조회하는 메서드
    getFoodTruckOverviews(lastFoodTruckId, updateFoodTruckId, handleFail);
  }, []); // 의존성 배열이 필요하면 추가합니다.

  return (
    <FoodTruckLayout>
      <div style={{ flexGrow: 1 }}>
        <div className="header">
          <BackSpaceHome></BackSpaceHome>
          <img src={ModifyButton} alt=""  onClick={navigateToModifyPage}/>
        </div>
        <img className="headerImage" src={foodTruck.foodTruck.foodTruckImageUrl} alt="" />
        <div className="storeInfo">
          <div className="foodTruckName">
            <span>{foodTruck.foodTruck.foodTruckName}</span>
            {/* <img src={EmptyHeart} alt="" /> */}
          </div>
          {/* <div className="location">
            <span>{foodTruck.foodTruckDetail.address} </span>

            <span> {foodTruck.foodTruckDetail.distance}m </span>
            <img src={Pin} alt="" />
          </div> */}
          <div className="review">
            <div>
              {foodTruck.foodTruck.reviewCount === 0 ? (
                <span>등록된 리뷰가 없습니다.</span>
              ) : (
                <>
                  <img src={Star} alt="" />
                  <span>{Math.ceil(foodTruck.foodTruck.avgGrade * 10) / 10}</span>
                  <span>({foodTruck.foodTruck.reviewCount})</span>
                </>
              )}
            </div>
            {foodTruck.foodTruck.isOpen ? <span id="open">open</span> : <span id="close">close</span>}
          </div>
          <span style={{ textAlign: 'left' }}>{foodTruck.foodTruck.content}</span>
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
        {selectedButton === 1 && foodTruck.menus.length > 0 && (
          <FoodTruckMenu menus={foodTruck.menus} />
        )}
        {selectedButton === 2 && (
          <FoodTruckInfo foodTruckDetail={foodTruck.foodTruck} schedules={foodTruck.schedules}></FoodTruckInfo>
        )}
        {selectedButton === 3 && <FoodTruckReview reviews={foodTruck.reviews}></FoodTruckReview>}
        <Navbar/>
      </div>
    </FoodTruckLayout>
  );
}

export default FoodTruckPage;
