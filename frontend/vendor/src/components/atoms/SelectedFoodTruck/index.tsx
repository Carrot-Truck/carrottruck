import { SelectedFoodTruckWrapper } from "./style";

interface ISelectedFoodTruckProps {
  selectedFoodTruck: string;
}

function SelectedFoodTruck({ selectedFoodTruck }: ISelectedFoodTruckProps) {
  return (
    <SelectedFoodTruckWrapper>
      <span>{selectedFoodTruck}</span>
    </SelectedFoodTruckWrapper>
  );
}

export default SelectedFoodTruck;
