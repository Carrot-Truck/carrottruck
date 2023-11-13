import { editSelectedFoodTruck } from "api/foodtruck/foodTruck";
import { FoodTruckListContainer } from "./style";
import { AxiosResponse } from "axios";
import FoodTruckListItem from "components/atoms/FoodTruckListItem";

interface IFoodTruckListItem {
  foodTruckId: number;
  foodTruckName: string;
  selected: boolean;
}

interface IFoodTruckListProps {
  foodTruckList: IFoodTruckListItem[];
  setSelectedFoodTruck: React.Dispatch<React.SetStateAction<number | null>>;
  setDetailViewable: React.Dispatch<React.SetStateAction<boolean>>;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function FoodTruckList({
  foodTruckList,
  setSelectedFoodTruck,
  setDetailViewable,
}: IFoodTruckListProps) {
  const handleFoodTruckListItemClick = async (foodTruckId: number) => {
    editSelectedFoodTruck(
      foodTruckId,
      (response: AxiosResponse) => {
        const data = getData(response);
        setSelectedFoodTruck(data);
        setDetailViewable(false);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

  return (
    <FoodTruckListContainer>
      {foodTruckList.length !== 0 &&
        foodTruckList.map((data: IFoodTruckListItem, index: number) => (
          <FoodTruckListItem
            key={index}
            foodTruckName={data.foodTruckName}
            selected={data.selected}
            handleFoodTruckListItemClick={() => handleFoodTruckListItemClick(data.foodTruckId)}
          />
        ))}
    </FoodTruckListContainer>
  );
}

export default FoodTruckList;
