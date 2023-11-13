import { useState, useEffect, ChangeEvent } from 'react';
import { FoodTruckModifyContainer } from './style';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
import { editFoodTruck } from 'api/foodtruck/foodTruck';
import { AxiosResponse, AxiosError } from 'axios';
import { getCategories } from 'api/foodtruck/category';

function FoodTruckModifyForm({ foodTruck }: any) {
  const navigate = useNavigate();

  // 이미 선언된 useState를 활용
  const [isDone, setIsDone] = useState(false);
  const [categoryId, setCategoryId] = useState(0);
  const [foodTruckName, setFoodTruckName] = useState('');
  const [foodTruckPicture, setFoodTruckPicture] = useState<File | null>(null);
  const [phoneNumber, setPhoneNumber] = useState('');
  const [content, setContent] = useState('');
  const [originInfo, setOriginInfo] = useState('');
  const [prepareTime, setPrepareTime] = useState(0);
  const [waitLimits, setWaitLimits] = useState(0);
  const [categories, setCategories] = useState<Category[]>([]);

  type Category = {
    categoryId: number;
    categoryName: string;
  } 

  const handleSuccess = (response: AxiosResponse) => {
    if (response.data.code === 200) {
      navigate('/foodtruck/menu/modify', { state: { foodTruck } });
    } else {
      alert('푸드트럭 등록 실패!\n다시 시도해주세요.');
      navigate('/');
    }
  };

  const handleFail = (response: AxiosError) => {
    console.log('Error ', response);
    alert('푸드트럭 등록 실패!\n다시 시도해주세요.');
    navigate('/');
  };

  const regist = async () => {
    if (isDone) {
      try {
        const formData = new FormData();
        if (foodTruckPicture !== null) {
          formData.append('file', foodTruckPicture);
        }

        const requestData = {
          categoryId,
          foodTruckName,
          phoneNumber,
          content,
          originInfo,
          prepareTime,
          waitLimits,
        };

        formData.append(
          'request',
          new Blob([JSON.stringify(requestData)], { type: 'application/json' })
        );

        console.log(requestData);

        editFoodTruck(foodTruck.foodTruckId, formData, handleSuccess, handleFail);
      } catch (error) {
        console.error('에러야...', error);
      }
    }

    if (!foodTruckName) {
      alert('푸드트럭 이름을 입력해주세요');
    } else if (!categoryId) {
      alert('카테고리를 골라주세요');
    } else if (!content) {
      alert('소갯말을 입력해주세요');
    } else if (!phoneNumber) {
      alert('가게 번호를 입력해주세요');
    } else if (!prepareTime) {
      alert('예상 준비시간을 입력해주세요');
    } else if (!waitLimits) {
      alert('대기 주문 최대 횟수를 입력해주세요');
    } else if (!originInfo) {
      alert('원산지 정보를 입력해주세요');
    }
    };
    
    const fetchCategories = async () => {
        getCategories(
            (response: any) => {
                console.log(response.data);
                setCategories(response.data.data.categories);
            },
            (error: any) => {
                console.error('API Error: ', error);
            }
        );
    }

    useEffect(() => {
        fetchCategories();
    setCategoryId((prevCategoryId) => foodTruck.categoryId || prevCategoryId)
    setFoodTruckName((prevFoodTruckName) => foodTruck.foodTruckName || prevFoodTruckName);
    setFoodTruckPicture((prevFoodTruckPicture) => foodTruck.foodTruckImageUrl || prevFoodTruckPicture);
    setPhoneNumber((prevPhoneNumber) => foodTruck.phoneNumber || prevPhoneNumber);
    setContent((prevContent) => foodTruck.content || prevContent);
    setOriginInfo((prevOriginInfo) => foodTruck.originInfo || prevOriginInfo);
    setPrepareTime((prevPrepareTime) => foodTruck.prepareTime || prevPrepareTime);
    setWaitLimits((prevWaitLimits) => foodTruck.waitLimits || prevWaitLimits);
      

    if (
      foodTruck.foodTruckName &&
      foodTruck.categoryId &&
      foodTruck.foodTruckImageUrl &&
      foodTruck.content &&
      foodTruck.phoneNumber &&
      foodTruck.prepareTime &&
      foodTruck.waitLimits &&
      foodTruck.originInfo
    ) {
      setIsDone(true);
    } else {
      setIsDone(false);
    }
  }, [
    foodTruck.foodTruckName,
    foodTruck.foodTruckImageUrl,
    foodTruck.categoryId,
    foodTruck.content,
    foodTruck.phoneNumber,
    foodTruck.prepareTime,
    foodTruck.waitLimits,
    foodTruck.originInfo,
  ]);

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files && event.target.files[0];
    if (file) {
      setFoodTruckPicture(file);
    }
  };

  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setCategoryId(Number(event.target.value));
  };

  return (
    <FoodTruckModifyContainer>
      <div className="regist-form">
        <div className="input">
          <span>푸드트럭 이름</span>
          <Input
            placeholder="푸드트럭이름을 입력해주세요"
            value={foodTruckName}
            setValue={setFoodTruckName}
            type="text"
          />
        </div>
        <div className="input">
          <span>푸드트럭 사진</span>
          <input className="truckPicture" id="picture" type="file" onChange={handleFileChange} />
          <label htmlFor="picture"> {foodTruckPicture && foodTruckPicture.name}</label>
        </div>
        <div className="input">
          <span>메뉴 카테고리</span>
          <select value={categoryId} onChange={handleCategoryChange}>
            <option value="">메뉴카테고리를 선택해주세요</option>
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </select>
        </div>
        <div className="input">
          <span>가게 소갯말</span>
          <Input placeholder="가게 소갯말을 입력해주세요" value={content} setValue={setContent} type="text" />
        </div>
        <div className="input">
          <span>전화번호</span>
          <Input placeholder="전화번호를 입력해주세요" value={phoneNumber} setValue={setPhoneNumber} type="text" />
        </div>
        <div className="input">
          <span>예상 준비 시간</span>
          <span> ex.15(분)</span>
          <Input
            placeholder="예: 15(분)"
            value={prepareTime}
            setValue={setPrepareTime}
            type="number"
          />
        </div>
        <div className="input">
          <span>대기 주문 최대 갯수</span>
          <Input
            placeholder="받을 수 있는 최대 주문 갯수입니다. 해당갯수 초과시 주문을 받지 않습니다."
            value={waitLimits}
            setValue={setWaitLimits}
            type="number"
          />
        </div>
        <div className="input">
          <span>원산지 정보</span>
          <Input
            placeholder="재료의 원산지들을 입력해 주세요"
            value={originInfo}
            setValue={setOriginInfo}
            type="text"
          />
        </div>
      </div>
      <Button
        handleClick={regist}
        color={isDone ? 'Primary' : 'SubFirst'}
        size="full"
        radius="m"
        text={isDone ? '다음(1/2)' : '필수 정보 입력'}
      />
    </FoodTruckModifyContainer>
  );
}

export default FoodTruckModifyForm;
