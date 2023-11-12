import { useState, ChangeEvent } from 'react';
import { FoodMenuDetailModifyLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import { createMenu } from 'api/menu';
import { AxiosError } from 'axios';
import { useNavigate } from 'react-router-dom';

function FoodMenuDetailModifyPage() {
  const navigate = useNavigate();
  const [menuName, setMenuName] = useState<string>('');
  const [menuImage, setMenuImage] = useState<File>();
  const [menuPrice, setMenuPrice] = useState<number>(0);
  const [menuDescription, setMenuDescription] = useState<string>('');

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    // 파일 리스트에서 첫 번째 파일을 가져오기 위한 타입 가드
    const file = event.target.files && event.target.files[0];
    if (file) {
      setMenuImage(file);
    }
  };

  const handleSuccess = () => {
    navigate(-1);
  }

  const handleFail = (response: AxiosError) =>{
    console.log(response);
    alert("메뉴 등록 중 에러 발생!");
    navigate('/');
  }

  const createMenuRequest = async () => {
    if (!menuImage || !menuName || menuPrice <= 0 || !menuDescription) {
      alert('모든 필드를 채워주세요');
      return;
    }

    const formData = new FormData();
    formData.append('file', menuImage);
    const requestData = {
      // 예시 데이터, 실제 데이터 구조에 맞게 조정하세요
      foodTruckId: 1, // 실제 foodTruckId를 적절히 설정해야 합니다
      menuName: menuName,
      price: menuPrice,
      description: menuDescription,
      menuOptions: [/* 메뉴 옵션 데이터 */]
    };
    formData.append('request', new Blob([JSON.stringify(requestData)], { type: 'application/json' }));

    createMenu(formData, handleSuccess, handleFail);
  };

  return (
    <FoodMenuDetailModifyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>메뉴편집</p>
      </div>
      <div className="inputField">
        <p>메뉴 이름</p>
        <Input type="text" value={menuName} setValue={setMenuName} placeholder="메뉴 이름을 입력해 주세요." />
      </div>
      <div className="inputField">
        <p>메뉴 사진</p>
        <div className="pictureWrapper">
          <input className="menuPicture" id="picture" type="file" onChange={handleFileChange} />
          <label htmlFor="picture">{menuImage ? menuImage.name : '이미지를 선택해 주세요'}</label>
        </div>
      </div>
      <div className="inputField">
        <p>메뉴 가격</p>
        <Input type="number" value={menuPrice} setValue={setMenuPrice} placeholder="메뉴 가격을 입력해 주세요." />
      </div>
      <div className="inputField">
        <p>메뉴 설명</p>
        <Input
          type="text"
          value={menuDescription}
          setValue={setMenuDescription}
          placeholder="메뉴에 대한 설명을 입력해 주세요."
        />
      </div>
      <Button size="full" radius="m" text="등록하기" color="Primary" handleClick={createMenuRequest}></Button>
    </FoodMenuDetailModifyLayout>
  );
}

export default FoodMenuDetailModifyPage;
