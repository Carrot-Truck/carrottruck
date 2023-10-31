import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MainPageLayout } from './style';
import Button from '../../components/atoms/Button';
import SearchBar from '../../components/atoms/SearchBar';
import Input from '../../components/atoms/Input';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';

function MainPage() {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/vendor/login');
  };
  const [searchValue, setSearchValue] = useState('');
  const [email, setEmail] = useState('');
  const [isVerified, setIsVerified] = useState(false);

  return (
    <MainPageLayout>
      <Button size="full" radius="m" color="Primary" text="어우 귀찮아..." handleClick={handleClick}></Button>
      <SearchBar
        size="l"
        confirmSearch={() => {}}
        value={searchValue}
        setValue={setSearchValue}
        color="Primary"
        placeholder="검색어를 입력하세요"
      />
      <Input placeholder="바니바니당근당근" value={email} setValue={setEmail} type="text" disabled={isVerified} />
      <FoodTruckMenu></FoodTruckMenu>
    </MainPageLayout>
  );
}

export default MainPage;
