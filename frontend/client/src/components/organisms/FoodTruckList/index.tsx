import { FoodTruckListContainer } from './style';
import FoodTruckListItem from 'components/atoms/FoodTruckListItem';

interface FoodTruck {
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

interface IFoodTruckListProps {
  foodTrucks: FoodTruck[];
}

function FoodTruckList({ foodTrucks }: IFoodTruckListProps) {
  return (
    <FoodTruckListContainer>
      {foodTrucks.map((foodTruck, index) => (
        <FoodTruckListItem key={index} {...foodTruck} />
      ))}
    </FoodTruckListContainer>
  );
}

export default FoodTruckList;
