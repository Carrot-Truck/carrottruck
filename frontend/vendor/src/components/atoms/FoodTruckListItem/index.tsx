import { FoodTruckListItemWrapper } from "./style";

interface IFoodTruckListItemProps {
  foodTruckName: string;
  selected: boolean;
  handleFoodTruckListItemClick: () => {};
}

function FoodTruckListItem({
  foodTruckName,
  selected,
  handleFoodTruckListItemClick,
}: IFoodTruckListItemProps) {
  return (
    <FoodTruckListItemWrapper onClick={handleFoodTruckListItemClick} selected={selected}>
      {foodTruckName}
      {selected && "(선택됨)"}
    </FoodTruckListItemWrapper>
  );
}

export default FoodTruckListItem;
