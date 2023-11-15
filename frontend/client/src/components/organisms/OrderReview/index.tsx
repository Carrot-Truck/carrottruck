import { useEffect, useState } from 'react';
import { OrderListFormContainer } from './styled';
import { useLocation, useNavigate } from 'react-router-dom';
import { AxiosError, AxiosResponse } from 'axios';
import { getMyOrderReview } from 'api/review';
import MyReview from 'components/atoms/MyReview';

interface Review {
  reviewId: number,
  reviewFoodTruckDto: {
    foodTruckName: string,
    foodTruckId: number
  },
  grade: number,
  createdDate: string,
  content: string,
  imageUrl: string
}

function OrderReview() {
  const navigate = useNavigate();
  const location = useLocation();
  const [review, setReview] = useState<Review>({
    reviewId: 0,
    reviewFoodTruckDto: {
      foodTruckName: '',
      foodTruckId: 0
    },
    grade: 0,
    createdDate: '',
    content: '',
    imageUrl: ''
  });

  const handleSuccess = (response : AxiosResponse) => {
    console.log("organisms/OrderReview/handleSuccess ", response.data);
    setReview(response.data.data);
  }

  const handleFail = (error: AxiosError) => {
    console.log(error);
    alert('내역 조회 중 문제가 생겼습니다.\n관리자에게 문의하세요.');
    navigate('/');
  }

  

  useEffect(()=>{
    getMyOrderReview(location.state.orderId, handleSuccess, handleFail);
  }, []);

  return (
    <OrderListFormContainer>
      {Object.keys(review).length === 0 ? (
        // 리뷰가 없을 때: 리뷰 작성 뷰
        <MyReview 
          foodTruckName={review.reviewFoodTruckDto.foodTruckName}
          grade={review.grade}
          createdDate={review.createdDate}
          content={review.content}
          imageUrl={review.imageUrl}
          writable={true}
        />
      ) : (
        // 리뷰가 있을 때: 리뷰 조회
        <MyReview 
          foodTruckName={review.reviewFoodTruckDto.foodTruckName}
          grade={review.grade}
          createdDate={review.createdDate}
          content={review.content}
          imageUrl={review.imageUrl}
          writable={false}
        />
      )}
    </OrderListFormContainer>
  );
}

export default OrderReview;
