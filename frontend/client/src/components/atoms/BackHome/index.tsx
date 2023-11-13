// import React from 'react';
import { BackHomeWrapper } from './style';
import back from '../../../assets/icons/keyboard-backspace.svg';
import useMovePage from 'hooks/useMovePage';

function BackHome() {
  const [movePage] = useMovePage();
  const handleBackClick = () => {
    movePage('/');
  };

  return (
    <BackHomeWrapper onClick={handleBackClick}>
      <img src={back} alt="" />
    </BackHomeWrapper>
  );
}

export default BackHome;
