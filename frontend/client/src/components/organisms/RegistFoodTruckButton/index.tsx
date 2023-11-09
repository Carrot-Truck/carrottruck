import Button from "components/atoms/BigButton";
import { useNavigate } from 'react-router-dom';
import { ReigsterFoodTrcukFormContainer } from './style';

function RegistFoodTruckButton() {
    const navigate = useNavigate();

    const register = async () => {
        try{
            navigate('/registration');
        } catch(error){
            console.log(error);
            navigate('/');
        }
    };

    return (
        <ReigsterFoodTrcukFormContainer>
            <Button text="푸드트럭 등록" color="Primary" size="big" radius="s" handleClick={register} />
        </ReigsterFoodTrcukFormContainer>
    );
};

export default RegistFoodTruckButton;