import BIGButton from 'components/atoms/BigButton';
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
import { ReigsterFoodTrcukFormContainer } from './style';

function VendorMainForm() {
  const navigate = useNavigate();

  const register = async () => {
    try {
      navigate('/registration');
    } catch (error) {
      console.log(error);
      navigate('/');
    }
  };

  const start = async () => {
    try {
      // 이동하면 그 페이지에서는 /food-truck/vendor 로 post 매핑해서 값 넘겨야해
      navigate('/start-sale');
    } catch (error) {
      console.log(error);
      navigate('/');
    }
  };

  return (
    <ReigsterFoodTrcukFormContainer>
      <BIGButton text="영업 시작" color="Primary" size="big" radius="s" handleClick={start} />
      <Button text="푸드트럭 등록" color="Primary" size="l" radius="s" handleClick={register} />
    </ReigsterFoodTrcukFormContainer>
  );
}

export default VendorMainForm;
