import { useState } from 'react';
import { MyPageLayout } from './style';
import BackHome from 'components/atoms/BackHome';
import Navbar from 'components/organisms/Navbar';
import NoteBook from 'assets/icons/notebook.svg';
import Pencil from 'assets/icons/pencil.svg';
import Heart from 'assets/icons/filled_heart.svg';
import MyReviewForm from 'components/organisms/MyReviewsForm';
import { useNavigate } from 'react-router-dom';

function MyPage() {
  const navigate = useNavigate();
  const [selectedButton, setSelectedButton] = useState<number>(1);
  const userData = {
    name: '김동현',
    nickname: '매미킴',
    email: 'ssafy@gmail.com',
    phoneNumber: '010-1234-1234',
    role: 'CLIENT'
  };

  // 클릭 핸들러 함수
  const handleClick = (buttonId: number) => {
    setSelectedButton(buttonId);
  };

  const handleMyInfo = () => {
    navigate("/mypage/myinfo");
  }

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  }

  return (
    <MyPageLayout>
      <div className="header">
        <BackHome></BackHome>
      </div>
      <div className="userInfo" onClick={() => handleMyInfo()}>
        <p>{userData.nickname}</p>
        <p> 님 &gt;</p>
      </div>
      <div className="history">
        <div className="orderList" onClick={() => handleClick(1)}>
          <img src={NoteBook} alt="" />
          <p>주문내역</p>
        </div>
        <div className="myReviews" onClick={() => handleClick(2)}>
          <img src={Pencil} alt="" />
          <p>리뷰관리</p>
        </div>
        <div className="myJjim" onClick={() => handleClick(3)}>
          <img src={Heart} alt="" />
          <p>찜</p>
        </div>
      </div>
      {selectedButton && <MyReviewForm></MyReviewForm>}
      <Navbar></Navbar>
      <p onClick={handleLogout}>로그아웃</p>
    </MyPageLayout>
  );
}

export default MyPage;
