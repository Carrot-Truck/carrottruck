import { ReviewListContainer } from './style';
import ReviewListItem from 'components/atoms/ReviewListItem';

function ReviewList() {
  const foodTruckReviews = [
    {
      reviewId: 1,
      nickname: '동혀니',
      grade: 5,
      content: '된장이 맛있어요',
      imageUrl:
        'https://carrottruck.s3.ap-northeast-2.amazonaws.com/%EB%8B%B9%EA%B7%BC%ED%8A%B8%EB%9F%AD%EB%A1%9C%EA%B3%A0.png'
    },
    {
      reviewId: 2,
      nickname: '후후후',
      grade: 2,
      content: '맛있다고해서 왔는데 먹고 토했어요',
      imageUrl:
        'https://carrottruck.s3.ap-northeast-2.amazonaws.com/%EB%8B%B9%EA%B7%BC%ED%8A%B8%EB%9F%AD%EB%A1%9C%EA%B3%A0.png'
    }
  ];
  return (
    <ReviewListContainer>
      {foodTruckReviews.map((foodTruckReview) => (
        <ReviewListItem key={foodTruckReview.reviewId} {...foodTruckReview}></ReviewListItem>
      ))}
    </ReviewListContainer>
  );
}

export default ReviewList;
