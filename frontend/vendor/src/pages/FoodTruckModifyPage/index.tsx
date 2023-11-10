import { FoodTruckModifyLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import FoodTruckModifyForm from 'components/organisms/FoodTruckModifyForm';

function FoodTruckModifyPage() {
  return (
    <FoodTruckModifyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>푸드트럭 수정</p>
      </div>
      <FoodTruckModifyForm></FoodTruckModifyForm>
    </FoodTruckModifyLayout>
  );
}

export default FoodTruckModifyPage;
