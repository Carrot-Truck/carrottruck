import { MyReviewsFormContainer } from './style';
import MyReview from 'components/atoms/MyReview';

function MyReviewForm() {
  const myReviewsList = [
    {
      reviewId: 1,
      reviewFoodTruckDto: {
        foodTruckName: '내가만든푸드트럭',
        foodTruckId: 1
      },
      grade: 5,
      createdDate: [2023, 11, 13, 9, 57, 52, 956459500],
      content: '여기 맛집이다!!',
      imageUrl:
        'https://carrottruck.s3.ap-northeast-2.amazonaws.com/review/7e46bb52-07de-4077-b382-feee759e89a7%EC%A7%B1%EA%B5%AC.jpg'
    },
    {
      reviewId: 2,
      reviewFoodTruckDto: {
        foodTruckName: '김동현 된장삼겹',
        foodTruckId: 2
      },
      grade: 1,
      createdDate: [2023, 11, 10, 9, 57, 52, 956459500],
      content: '여기 진짜 별로에요. 서비스도 최악이고 다신 안올거에요.',
      imageUrl:
        'https://carrottruck.s3.ap-northeast-2.amazonaws.com/%EB%8B%B9%EA%B7%BC%ED%8A%B8%EB%9F%AD%EB%A1%9C%EA%B3%A0.png'
    }
  ];
  return (
    <MyReviewsFormContainer>
      <div className="title">
        <p>리뷰내역</p>
      </div>
      {myReviewsList.map((myreview) => (
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
