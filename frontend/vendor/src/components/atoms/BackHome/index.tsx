// import React from 'react';
import { BackHomeWrapper } from './style';
import back from '../../../assets/icons/keyboard-backspace.svg';
// import useMovePage from 'hooks/useMovePage';
import { useNavigate } from 'react-router-dom';

function BackHome() {
  // const [movePage] = useMovePage();
  const navigate = useNavigate();
  const handleBackClick = () => {
    navigate('/');
  };

  return (
    <BackHomeWrapper onClick={handleBackClick}>
      <img src={back} alt="" />
    </BackHomeWrapper>
  );
}

export default BackHome;
