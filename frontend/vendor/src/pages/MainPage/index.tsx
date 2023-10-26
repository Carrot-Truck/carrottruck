import React from 'react';
import styled from 'styled-components';
import Button from '../../components/atoms/Button';
import SearchBar from '../../components/atoms/SearchBar';

const Layout = styled.div`
  margin: 0 320px;
  padding-bottom: 200px;
`;

function MainPage() {
  const handleClick = () => {};
  return (
    <Layout>
      <Button size="l" radius="m" color="Normal" text="어우 귀찮아..." handleClick={handleClick}></Button>
      {/* <SearchBar
        size="l"
        confirmSearch={() => {}}
        value="흠"
        setValue={setSearchValue}
        color="SubFirst"
        placeholder="검색어를 입력하세요"
      /> */}
    </Layout>
  );
}

export default MainPage;
