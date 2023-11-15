import { MyReviewItemWrapper } from './style';
import Star from 'assets/icons/star.svg';
import EmptyStar from 'assets/icons/star_empty.svg';

interface IMyReviewProps {
  foodTruckName: string;
  grade: number;
  createdDate: string;
  content: string;
  imageUrl: string;
}

function MyReview(props: IMyReviewProps) {
  const { foodTruckName, grade, createdDate, content, imageUrl } = props;

  // 채워진 별과 빈 별을 생성
  const filledStars = Array(grade).fill(<img src={Star} alt="Filled Star" />);
  const emptyStars = Array(5 - grade).fill(<img src={EmptyStar} alt="Empty Star" />);

  const splitDate = createdDate.split("T");
  const datePart = splitDate[0]; // 날짜 부분
  const timePart = splitDate[1]; // 시간 부분

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
        작성일자: {datePart} {timePart}
      </p>
      <p>{content}</p>
      <div className="reviewImage">
        {imageUrl && <img src={imageUrl} alt="Review" />} {/* imageUrl이 유효할 때만 이미지를 렌더링 */}
      </div>
    </MyReviewItemWrapper>
  );
}

export default MyReview;
