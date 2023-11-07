import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MainPageLayout } from './style';
import Button from '../../components/atoms/Button';
import SearchBar from '../../components/atoms/SearchBar';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';
import NaverMap from 'components/atoms/Map';
import Navbar from 'components/organisms/Navbar';

function MainPage() {
  const MapID = '0n8mf3dv6k';
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/login');
  };
  const [searchValue, setSearchValue] = useState('');

  return (
    <MainPageLayout>
      <Button size="full" radius="m" color="Primary" text="어우 귀찮아..." handleClick={handleClick}></Button>
      <SearchBar
        size="full"
        confirmSearch={() => {}}
        value={searchValue}
        setValue={setSearchValue}
        color="Primary"
        placeholder="검색어를 입력하세요"
      />
      <NaverMap clientId={MapID} markers={[{ latitude: 35.19398974745785, longitude: 126.813153026795 }]}></NaverMap>
      <FoodTruckMenu></FoodTruckMenu>
      <Navbar></Navbar>
    </MainPageLayout>
  );
}

export default MainPage;
