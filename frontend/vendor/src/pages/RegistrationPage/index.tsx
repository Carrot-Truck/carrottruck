import { useState } from 'react';
import { BusinessRegistrationLayout } from './style';
import SwitchButton from 'components/organisms/SwitchButton';
import BusinessRegistration from 'components/organisms/BusinessRegistration';
import FoodTruckRegistrationForm from 'components/organisms/FoodTruckRegistration';
import BackSpace from 'components/atoms/BackSpace';

function RegistrationPage() {
  const [selectedButton, setSelectedButton] = useState(1);

  return (
    <BusinessRegistrationLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>환영해요!</p>
      </div>
      <SwitchButton
        selectedButton={selectedButton}
        setSelectedButton={setSelectedButton}
        firstButton="사업자인증"
        secondButton="푸드트럭등록"
      ></SwitchButton>
      {selectedButton === 1 && <BusinessRegistration />}
      {selectedButton === 2 && <FoodTruckRegistrationForm />}
    </BusinessRegistrationLayout>
  );
}

export default RegistrationPage;
