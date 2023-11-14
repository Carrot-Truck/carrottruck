import { MyReviewItemWrapper } from './style';
import Star from 'assets/icons/star.svg';
import EmptyStar from 'assets/icons/star_empty.svg';

interface IMyReviewProps {
  foodTruckName: string;
  grade: number;
  createdDate: number[];
  content: string;
  imageUrl: string;
}

function MyReview(props: IMyReviewProps) {
  const { foodTruckName, grade, createdDate, content, imageUrl } = props;

  // 채워진 별과 빈 별을 생성
  const filledStars = Array(grade).fill(<img src={Star} alt="Filled Star" />);
  const emptyStars = Array(5 - grade).fill(<img src={EmptyStar} alt="Empty Star" />);

  return (
    <MyReviewItemWrapper>
      <div className="name">
        <p>{foodTruckName}</p>
      </div>
      <div>
        {filledStars}
        {emptyStars}
      </div>
      <p>
        작성일자: {createdDate[0]}-{createdDate[1]}-{createdDate[2]}
      </p>
      <p>{content}</p>
      <div className="reviewImage">
        <img src={imageUrl} alt="" />
      </div>
    </MyReviewItemWrapper>
  );
}

export default MyReview;
