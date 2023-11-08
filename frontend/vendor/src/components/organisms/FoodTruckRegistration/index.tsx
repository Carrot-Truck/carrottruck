import { useState, useEffect, ChangeEvent } from 'react';
import { FoodTruckRegistrationContainer } from './style';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';

function FoodTruckRegistrationForm() {
  const [isDone, setIsDone] = useState(false);
  const [categoryId, setCategoryId] = useState(0); // 카테고리 ID를 문자열로 저장
  const [foodTruckName, setfoodTruckName] = useState('');
  const [foodTruckPicture, setFoodTruckPicture] = useState<File | null>(null);
  const [phoneNumber, setphoneNumber] = useState('');
  const [content, setContent] = useState('');
  const [originInfo, setOriginInfo] = useState('');
  const [prepareTime, setPrepareTime] = useState(0);
  const [waitLimits, setWaitLimits] = useState(0);

  const categories = [
    { id: '1', name: '한식' },
    { id: '2', name: '중식' },
    { id: '3', name: '일식' }
    // 다른 카테고리들...
  ];

  const regist = async () => {
    if (isDone) {
      try {
        return;
      } catch (error) {
        console.error('에러야...', error);
      }
    }
    if (!foodTruckName) {
      alert('푸드트럭 이름을 입력해주세요');
    } else if (!foodTruckPicture) {
      alert('푸드트럭 사진을 넣어주세요');
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

  useEffect(() => {
    if (
      foodTruckName &&
      foodTruckPicture &&
      categoryId &&
      content &&
      phoneNumber &&
      prepareTime &&
      waitLimits &&
      originInfo
    ) {
      setIsDone(true);
    } else {
      setIsDone(false);
    }
  }, [foodTruckName, foodTruckPicture, categoryId, content, phoneNumber, prepareTime, waitLimits, originInfo]);

  // 파일이 선택되었을 때 호출될 핸들러입니다.
  // ChangeEvent<HTMLInputElement>는 TypeScript에서 input 변경 이벤트의 타입입니다.
  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    // 파일 리스트에서 첫 번째 파일을 가져오기 위한 타입 가드
    const file = event.target.files && event.target.files[0];
    if (file) {
      setFoodTruckPicture(file);
    }
  };

  // 드롭다운 변경 이벤트 핸들러
  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setCategoryId(Number(event.target.value));
  };

  return (
    <FoodTruckRegistrationContainer>
      <div className="regist-form">
        <div className="input">
          <span>푸드트럭 이름</span>
          <Input
            placeholder="푸드트럭이름을 입력해주세요"
            value={foodTruckName}
            setValue={setfoodTruckName}
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
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>
        <div className="input">
          <span>가게 소갯말</span>
          <Input placeholder="메뉴카테고리를 선택해주세요" value={content} setValue={setContent} type="text" />
        </div>
        <div className="input">
          <span>전화번호</span>
          <Input placeholder="전화번호를 입력해주세요" value={phoneNumber} setValue={setphoneNumber} type="text" />
        </div>
        <div className="input">
          <span>예상 준비 시간</span>
          <span> ex.15(분)</span>
          <Input placeholder="예: 15(분)" value={prepareTime} setValue={setPrepareTime} type="number" />
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
        text={isDone ? '푸드트럭등록' : '작성 중 이세요'}
      />
    </FoodTruckRegistrationContainer>
  );
}

export default FoodTruckRegistrationForm;