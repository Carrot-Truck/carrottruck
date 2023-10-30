import React, { useState } from 'react';
import styled from 'styled-components';
import Button from '../../components/atoms/Button';
import SearchBar from '../../components/atoms/SearchBar';
import Input from '../../components/atoms/Input';
import FoodTruckMenu from 'components/organisms/FoodTruckMenu';

const Layout = styled.div`
  margin: 0 320px;
  padding-bottom: 200px;
`;

function MainPage() {
  const handleClick = () => {};
  const [searchValue, setSearchValue] = useState('');
  const [email, setEmail] = useState('');
  const [isVerified, setIsVerified] = useState(false);

  return (
    <Layout>
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
    </Layout>
  );
}

export default MainPage;
