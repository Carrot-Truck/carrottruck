import { useState, useEffect } from 'react';
import { FoodTruckLayout } from './style';
import Navbar from "components/organisms/Navbar";
import BackSpace from 'components/atoms/BackSpace';
import Button from 'components/atoms/Button';
import { useNavigate, useParams } from 'react-router-dom';
// import Pocha from 'assets/imgs/image 21.png';
import EmptyHeart from 'assets/icons/empty_heart.svg';
import Pin from 'assets/icons/pin.svg';
import Star from 'assets/icons/star.svg';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';
import {getFoodTruck} from 'api/foodtruck/foodTruck';
import {getFoodTruckReview} from 'api/review'
import { AxiosResponse, AxiosError } from 'axios';
import { getSchedules } from 'api/schedule';
import FoodTruckInfo from 'components/organisms/FoodTruckInfo';
import FoodTruckReview from 'components/organisms/FoodTruckReview'

function FoodTruckPage() {
  const params = useParams();
  const foodTruckIdString = params.foodTruckId;
  const foodTruckId = foodTruckIdString ? parseInt(foodTruckIdString, 10) : null;

  const navigate = useNavigate();
  const [selectedButton, setSelectedButton] = useState(1);
  // ( 현재 선택된 푸드트럭 id 가져와야해 ) -> 우선순위 보류

  const buttonClick = (buttonNumber: number) => {
    setSelectedButton(buttonNumber);
  };

  const [foodTruck, setFoodTruck] = useState({
    foodTruck: 
      {
        foodTruckId : 0,
        foodTruckName : "",
        phoneNumber : "",
        content : "",
        originInfo : "",
        isOpen : false,
        isLiked : false,
        prepareTime : 30,
        avgGrade : 4.5,
        likeCount : 132,
        reviewCount : 1324,
        distance : 123.123,
        address : "",
        foodTruckImageUrl : "https://carrottruck.s3.ap-northeast-2.amazonaws.com/%EB%8B%B9%EA%B7%BC%ED%8A%B8%EB%9F%AD%EB%A1%9C%EA%B3%A0.png",
        selected : true,
        isNew : true,
        vendorName : "",
        tradeName : "",
        businessNumber: "",
      }
    ,
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

  const afterUpdateReview = (response: AxiosResponse) => {
    console.log('Review data fetched successfully', response);
    const foodTruckReviewDtoList = response.data.data.foodTruckDtoList;
    
    setFoodTruck(prevState =>{
      // 현재 상태와 비교하여 값이 다를 때만 업데이트
      if (prevState.reviews !== foodTruckReviewDtoList) {
        return {
          ...prevState,
          reviews: foodTruckReviewDtoList,
        };
      } else {
        return prevState; // 상태가 변하지 않으면 이전 상태를 반환하여 업데이트 방지
      }
    });

  }

  const afterUpdateSchedule = (response: AxiosResponse) => {
    console.log(response);
     
    setFoodTruck(prevState=> {
      if (prevState.schedules !== response.data.data.schedules) {
        return{
          ...prevState,
          schedules: response.data.data.schedules
        }
      } else {
        return prevState;
      }
    });
  }
  
  const updateFoodTruckId = (response: AxiosResponse) => {
    console.log(response);
     
    setFoodTruck(prevState=> {
      if (prevState.foodTruck !== response.data.data.foodTruck || prevState.menus !== response.data.data.menus) {
        return{
          ...prevState,
          foodTruck: response.data.data.foodTruck,
          menus: response.data.data.menus,
        }
      } else {
        return prevState;
      }
    });

    const foodTruckId = {
      foodTruckId: response.data.data.foodTruck.foodTruckId
    };

    getFoodTruckReview(response.data.data.foodTruck.foodTruckId, afterUpdateReview, handleFail);
    getSchedules(foodTruckId, afterUpdateSchedule, handleFail);
  };

  // handleFail 함수 수정
  const handleFail = (error: AxiosError) => {
    console.error('Error fetching review data', error);
    navigate(0);
  };

  useEffect(() => {
    if (foodTruckId === null || isNaN(foodTruckId)) {
      alert('올바른 접근이 아닙니다.');
      navigate('/');
      return;
    }
  
    const fetchLocationAndData = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const currentLocation = {
              latitude: position.coords.latitude,
              longitude: position.coords.longitude
            };
            getFoodTruck(foodTruckId, currentLocation, updateFoodTruckId, ()=>{alert("잘못된 접근입니다."); navigate('/login');});
          },
          (error) => {
            console.error('Geolocation Error:', error);
            alert('위치 정보를 가져오는데 실패했습니다.');
            navigate(-1);
          }
        );
      } else {
        alert('이 브라우저에서는 위치 정보를 사용할 수 없습니다.');
        navigate('/');
      }
    };
  
    fetchLocationAndData();
  }, []);  

  return (
    <FoodTruckLayout>
      <div style={{ flexGrow: 1 }}>
        <div className="header">
          <BackSpace></BackSpace>
        </div>
        <img className="headerImage" src={foodTruck.foodTruck.foodTruckImageUrl} alt="" />
        <div className="storeInfo">
          <div className="foodTruckName">
            <span>{foodTruck.foodTruck.foodTruckName}</span>
            <img src={EmptyHeart} alt="" />
          </div>
          <div className="location">
            {/* <span> {foodTruck.distance}m </span> */}
            <img src={Pin} alt="" />
            <span>{foodTruck.foodTruck.address === null ? ("등록된 주소가 없습니다.") : (foodTruck.foodTruck.address)} </span>
          </div>
          <div className="review">
            <div>
            {foodTruck.foodTruck.reviewCount === 0 ? (
                <span>등록된 리뷰가 없습니다.</span>
                ) : (
                  <>
                    <img src={Star} alt="" />
                    <span>({foodTruck.foodTruck.avgGrade})</span>
                    <span>{foodTruck.foodTruck.reviewCount}</span>
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
        {selectedButton === 3 && (
          <FoodTruckReview reviews={foodTruck.reviews}></FoodTruckReview>
        )}
        <Navbar/>
      </div>
    </FoodTruckLayout>
  );
}

export default FoodTruckPage;
