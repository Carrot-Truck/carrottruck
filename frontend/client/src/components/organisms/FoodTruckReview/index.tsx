// import React from 'react';
// import { useLocation } from 'react-router-dom';
import { FoodTruckInfoContainer } from './style';
import FoodTruckReviewItem from 'components/atoms/FoodTruckReviewtem'

interface Review {
  reviewId: number;
  nickname: string;
  grade: number;
  content: string;
  imageUrl: string;
}

interface FoodTruck {
  reviews: Review[];
}

function FoodTruckReview({ reviews }: FoodTruck) {
  if (!reviews || reviews.length === 0) {
    return (
      <FoodTruckInfoContainer>
        <p>리뷰가 없습니다.</p>
      </FoodTruckInfoContainer>
    );
  }

  return (
    <FoodTruckInfoContainer>
      {reviews.map((reviews, index) => (
        <FoodTruckReviewItem key={reviews.reviewId} reviewId={reviews.reviewId} nickname={reviews.nickname} content={reviews.content} grade={reviews.grade} imageUrl={reviews.imageUrl} />
      ))}
    </FoodTruckInfoContainer>
  );
}

export default FoodTruckReview;
