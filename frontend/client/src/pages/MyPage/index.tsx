import { useState, useEffect } from 'react';
import { MyPageLayout, LogoutP } from './style';
import BackHome from 'components/atoms/BackHome';
import Navbar from 'components/organisms/Navbar';
import NoteBook from 'assets/icons/notebook.svg';
import Pencil from 'assets/icons/pencil.svg';
import Heart from 'assets/icons/filled_heart.svg';
import MyReviewForm from 'components/organisms/MyReviewsForm';
import OrderListForm from 'components/organisms/OrderListForm';
import { useNavigate } from 'react-router-dom';
import { info } from 'api/member/client';
import { AxiosError, AxiosResponse } from 'axios';
import { getLikedFoodTruck } from 'api/foodtruck/foodTruck';
import FoodTruckList from 'components/organisms/FoodTruckList';

interface User {
  name: string;
  nickname: string;
  email: string;
  phoneNumber: string;
  role: string;
}
function MyPage() {
  const navigate = useNavigate();
  const [selectedButton, setSelectedButton] = useState<number>(1);
  const [userData, setUserData] = useState<User>({
    name: '',
    nickname: '',
    email: '',
    phoneNumber: '',
    role: ''
  });
  const [foodTruckList, setFoodTruckList] = useState<{ hasNext: boolean; items: any[] }>({ hasNext: false, items: [] });

  const toggleLike = (foodTruckId: number) => {
    setFoodTruckList((prevState) => {
      return {
        ...prevState,
        items: prevState.items.map((item) =>
          item.foodTruckId === foodTruckId ? { ...item, isLiked: !item.isLiked } : item
        )
      };
    });
  };

  // 클릭 핸들러 함수
  const handleClick = (buttonId: number) => {
    setSelectedButton(buttonId);
  };

  const handleMyInfo = () => {
    navigate('/mypage/myinfo');
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('grantType');
    localStorage.removeItem('selectedFoodTruckId');
    localStorage.removeItem('persist:user');
    navigate('/login');
  };

  const handleFailUserInfoUpdate = (error: AxiosError) => {
    console.log('Error at Mypage handleFailUserInfoUpdate : ', error);
    alert('로그인 정보가 없습니다.');
    navigate('/login');
  };

  const handleSuccess = (response: AxiosResponse) => {
    setFoodTruckList(response.data.data);
  };

  const handleFail = () => {
    alert('찜한 푸드트럭 조회 중 문제가 발생하였습니다.\n관리자에게 문의하세요.');
    navigate('/');
  };

  useEffect(() => {
    // TODO: 여기에 내가 좋아요 한 foodTruckList 받아서
    // 3 번 FoodTruckList 에 뿌려주기
    getLikedFoodTruck(handleSuccess, handleFail);
    info((response: AxiosResponse) => {
      setUserData(response.data.data);
    }, handleFailUserInfoUpdate);
  }, []);

  return (
    <MyPageLayout>
      <div className="header">
        <BackHome></BackHome>
        <LogoutP onClick={handleLogout}>로그아웃</LogoutP>
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
      {selectedButton === 1 && <OrderListForm></OrderListForm>}
      {selectedButton === 2 && <MyReviewForm></MyReviewForm>}
      {selectedButton === 3 && (
        <>
          <div className="title">
            <p>찜 내역</p>
          </div>
          <FoodTruckList foodTrucks={foodTruckList.items} onToggleLike={toggleLike} />
        </>
      )}
      <Navbar></Navbar>
    </MyPageLayout>
  );
}

export default MyPage;
