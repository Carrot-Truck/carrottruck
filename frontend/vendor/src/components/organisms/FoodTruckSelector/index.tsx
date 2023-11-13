import SelectedFoodTruck from "components/atoms/SelectedFoodTruck";
import { FoodTruckSelectorContainer } from "./style";
import ShowDetail from "components/atoms/ShowDetail";

interface IFoodTruckSelectorProps {
  selectedFoodTruckName: string;
  detailViewable: boolean;
  setDetailViewable: React.Dispatch<React.SetStateAction<boolean>>;
}

function FoodTruckSelector({
  selectedFoodTruckName,
  detailViewable,
  setDetailViewable,
}: IFoodTruckSelectorProps) {
  return (
    <FoodTruckSelectorContainer>
      <SelectedFoodTruck selectedFoodTruck={selectedFoodTruckName} />
      <ShowDetail viewable={detailViewable} setViewable={setDetailViewable} />
    </FoodTruckSelectorContainer>
  );
}

export default FoodTruckSelector;
