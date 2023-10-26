import React, { Dispatch, SetStateAction } from 'react';
import { ReactComponent as Search } from '../../../assets/icons/search.svg';
import { SearchBarContainer } from './style';

interface ISearchBarProps {
  size: 's' | 'l' | 'full';
  value: string;
  setValue: string;
  confirmSearch: () => void;
  placeholder: string;
  color: 'Primary' | 'SubFirst' | 'SubSecond';
}
function SearchBar(props: ISearchBarProps) {
  const { size, confirmSearch, value, setValue, placeholder, color } = props;

  return (
    <SearchBarContainer $size={size} $color={color}>
      {/* <input type="text" value={value} onChange={(e) => setValue(e.target.value)} placeholder={placeholder} /> */}
      <button type="button" className="confirm-search-btn-wrapper" onClick={confirmSearch}>
        <Search />
      </button>
    </SearchBarContainer>
  );
}

export default SearchBar;
