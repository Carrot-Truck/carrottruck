import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FoodMenuDetailLayout } from './style';
import BackSpace from 'components/atoms/BackSpace';
import Button from 'components/atoms/BigButton';
import FoodPicture from 'assets/imgs/meat.png';
import MenuOption from 'components/atoms/MenuOption';

function FoodMenuDetailPage() {
  const menu = {
    menu: {
      menuId: 2,
      menuName: '노른자 된장 삼겹살 덮밥',
      menuPrice: 6900,
      menuDescription: '감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥',
      menuSoldOut: false,
      menuImageUrl: 'imageUrl'
    },
    menuOptionCount: 2,
    menuOptions: [
      {
        menuOptionId: 1,
        menuOptionName: '양적게',
        menuOptionPrice: 0,
        menuOptionDescription: '',
        menuOptionSoldOut: false
      },
      {
        menuOptionId: 2,
        menuOptionName: '양많이',
        menuOptionPrice: 0,
        menuOptionDescription: '20%정도 많이 드려요',
        menuOptionSoldOut: false
      },
      {
        menuOptionId: 3,
        menuOptionName: '곱빼기',
        menuOptionPrice: 3000,
        menuOptionDescription: '정량의 1.5배로 드려요',
        menuOptionSoldOut: false
      }
    ]
  };

  const navigate = useNavigate();
  const menuModify = () => {
    navigate('/');
  };
  // 메뉴 옵션의 체크 상태를 관리하는 상태를 선언
  // 초기 상태로 모든 옵션을 체크되지 않은 상태(false)로 설정
  const [checkedOptions, setCheckedOptions] = useState<{ [key: string]: boolean }>(
    menu.menuOptions.reduce((acc: { [key: string]: boolean }, option) => {
      acc[option.menuOptionId.toString()] = false; // 키를 문자열로 변환
      return acc;
    }, {})
  );

  // 체크 상태를 토글하는 함수
  const toggleCheck = (id: number) => {
    setCheckedOptions((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  return (
    <FoodMenuDetailLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>메뉴 상세</p>
      </div>
      <img className="headerImage" src={FoodPicture} alt="" />
      <div className="menuInfo">
        <p>{menu.menu.menuName}</p>
        <p>{menu.menu.menuDescription}</p>
      </div>
      <div className="priceInfo">
        <p>가격</p>
        <p>{menu.menu.menuPrice} 원</p>
      </div>
      {menu.menuOptions.map((menuOption) => (
        <MenuOption
          key={menuOption.menuOptionId}
          menuOptionId={menuOption.menuOptionId}
          menuOptionName={menuOption.menuOptionName}
          menuOptionPrice={menuOption.menuOptionPrice}
          menuOptionDescription={menuOption.menuOptionDescription}
          menuOptionSoldOut={menuOption.menuOptionSoldOut}
          isChecked={checkedOptions[menuOption.menuOptionId]}
          onToggle={() => toggleCheck(menuOption.menuOptionId)}
        ></MenuOption>
      ))}
      <Button text="인증" color="Primary" size="full" radius="s" handleClick={menuModify} />
    </FoodMenuDetailLayout>
  );
}

export default FoodMenuDetailPage;
