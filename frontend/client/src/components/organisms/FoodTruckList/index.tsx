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
    onToggleLike: (foodTruckId: number) => void; 
}

function FoodTruckList({ foodTrucks, onToggleLike }: IFoodTruckListProps) {
  return (
    <FoodTruckListContainer>
      {foodTrucks.map((foodTruck, index) => (
        <FoodTruckListItem key={index} {...foodTruck } onToggleLike={onToggleLike}/>
      ))}
    </FoodTruckListContainer>
  );
}

export default FoodTruckList;
