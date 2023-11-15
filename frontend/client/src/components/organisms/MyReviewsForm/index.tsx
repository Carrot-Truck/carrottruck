import { useEffect, useState } from 'react';
import { MyReviewsFormContainer } from './style';
import MyReview from 'components/atoms/MyReview';
import { getMyReview } from 'api/review';
import { useNavigate } from 'react-router-dom';
import { AxiosResponse } from 'axios';

function MyReviewForm() {
  const navigate = useNavigate();
  const [myReviewsList, setMyReviewsList] = useState([]);

  const handleSuccess = (response : AxiosResponse) => {
    setMyReviewsList(response.data.data.myReviewDtoList);
  }

  const handleFail = () => {
    alert("리뷰 조회 중 문제가 발생했습니다.\n다시 시도해주세요.");
    navigate('/');
  }
  useEffect(()=>{
    getMyReview(handleSuccess, handleFail);
  }, [])

  return (
    <MyReviewsFormContainer>
      <div className="title">
        <p>리뷰내역</p>
      </div>
      {myReviewsList.map((myreview:any) => (
        <MyReview
          key={myreview.reviewId}
          foodTruckName={myreview.reviewFoodTruckDto.foodTruckName}
          grade={myreview.grade}
          createdDate={myreview.createdDate}
          content={myreview.content}
          imageUrl={myreview.imageUrl}
        ></MyReview>
      ))}
    </MyReviewsFormContainer>
  );
}

export default MyReviewForm;
