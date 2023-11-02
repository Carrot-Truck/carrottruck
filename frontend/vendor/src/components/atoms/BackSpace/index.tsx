// import React from 'react';
import { BackSpaceWrapper } from './style';
import back from '../../../assets/icons/keyboard-backspace.svg';
import useMovePage from 'hooks/useMovePage';

function BackSpace() {
  const [movePage] = useMovePage();
  const handleBackClick = () => {
    movePage('/vendor');
  };

  return (
    <BackSpaceWrapper onClick={handleBackClick}>
      <img src={back} alt="" />
    </BackSpaceWrapper>
  );
}

export default BackSpace;
