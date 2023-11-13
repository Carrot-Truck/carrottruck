import { FoodTruckListItemWrapper } from './style';
import Logo from 'assets/imgs/playstore.png';
import Pin from 'assets/icons/pin.svg';
import Empty from 'assets/icons/empty_heart.svg';
import Filled from 'assets/icons/filled_heart.svg';
import Star from 'assets/icons/star.svg';

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
  const { foodTruckName, address, distance, isLiked, grade, reviewCount, foodTruckImageUrl } = props;

  return (
    <FoodTruckListItemWrapper>
      <div className="restaurant">
        <div className="category">
          <img src={Logo} alt="" />
          <div className="name">
            <p>{foodTruckName}</p>
            <div className="location">
              <img src={Pin} alt="" />
              <p>
                {address}({distance}m)
              </p>
            </div>
          </div>
        </div>
        {isLiked ? <img src={Filled} alt="" /> : <img src={Empty} alt="" />}
      </div>
      <div className="reviews">
        <img src={Star} alt="" />
        <span>({grade})</span>
        <span>{reviewCount}</span>
      </div>
      <div className="foodTruckImage">
        <img src={foodTruckImageUrl} alt="" />
      </div>
    </FoodTruckListItemWrapper>
  );
}

export default FoodTruckListItem;
