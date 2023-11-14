import { FoodTruckListItemWrapper } from './style';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import Logo from 'assets/imgs/playstore.png';
import Empty from 'assets/icons/empty_heart.svg';
import Filled from 'assets/icons/filled_heart.svg';
import Star from 'assets/icons/star.svg';
import { foodTruckLike } from 'api/foodtruck/foodTruck';

interface IFoodTruckMenuItemProps {
  foodTruckScheduleId: number;
  categoryId: number;
  foodTruckId: number;
  foodTruckName: string;
  isOpen: boolean;
  isLiked: boolean;
  prepareTime: number;
  likeCount: number;
  grade: number;
  reviewCount: number;
  distance: number;
  address: string;
  foodTruckImageUrl: string;
  isNew: boolean;
}

function FoodTruckListItem(props: IFoodTruckMenuItemProps) {
    const { categoryId, foodTruckId, foodTruckName, address, distance, isLiked: initialIsLiked, grade, reviewCount, foodTruckImageUrl } = props;
    const [liked, setLiked] = useState(initialIsLiked);
    const navigate = useNavigate();

    useEffect(() => {
        setLiked(props.isLiked);
    }, [props.isLiked]);

    // onClick 핸들러 정의
    const handleClick = () => {
        // 예시: 특정 경로로 이동
        navigate(`/foodtruck/detail/${foodTruckId}`);
    };

    const handleLikeClick = (event: any) => {
        event.stopPropagation();
        foodTruckLike({
            foodTruckId: foodTruckId
        },
        (response: any) => {
            console.log(response.data.data);
            // 여기에서 isLiked 상태 업데이트
            setLiked(!liked); // 현재 isLiked 상태를 반대로 변경
        },
        (error: any) => {
            console.error('API Error: ', error);
        });
    };

  return (
    <FoodTruckListItemWrapper onClick={handleClick}>
      <div className="restaurant">
        <div className="category">
          <img src={require(`assets/icons/category${categoryId}.svg`)} alt="" />
          <div className="name">
            <p>{foodTruckName}</p>
          </div>
        </div>
        {liked ? <img src={Filled} alt="" onClick={handleLikeClick}/> : <img src={Empty} alt="" onClick={handleLikeClick}/>}
      </div>
      <div className="reviews">
        <img src={Star} alt="" />
        <span>({grade})</span>
        <span>{reviewCount}</span>
      </div>
      {/* <div className="foodTruckImage">
        <img src={foodTruckImageUrl} alt="" />
      </div> */}
    </FoodTruckListItemWrapper>
  );
}

export default FoodTruckListItem;