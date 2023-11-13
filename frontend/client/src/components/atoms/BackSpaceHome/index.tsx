// import React from 'react';
import { BackSpaceWrapper } from './style';
import back from '../../../assets/icons/keyboard-backspace.svg';
// import useMovePage from 'hooks/useMovePage';
import { useNavigate } from 'react-router-dom';

function BackSpaceHome() {
  // const [movePage] = useMovePage();
  const navigate = useNavigate();
  const handleBackClick = () => {
    navigate(-1);
  };

  return (
    <BackSpaceWrapper onClick={handleBackClick}>
      <img src={back} alt="" />
    </BackSpaceWrapper>
  );
}

export default BackSpaceHome;
