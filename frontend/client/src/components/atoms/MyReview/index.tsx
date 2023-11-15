import { MyReviewItemWrapper } from './style';
import Star from 'assets/icons/star.svg';
import EmptyStar from 'assets/icons/star_empty.svg';
import Button from '../Button'; // 버튼 컴포넌트 임포트

interface IMyReviewProps {
  foodTruckName: string;
  grade: number;
  createdDate: string;
  content: string;
  imageUrl: string;
  writable: boolean;
}

function MyReview(props: IMyReviewProps) {
  const { foodTruckName, grade, createdDate, content, imageUrl, writable } = props;

  // 채워진 별과 빈 별을 생성
  const filledStars = Array(grade).fill(<img src={Star} alt="Filled Star" />);
  const emptyStars = Array(5 - grade).fill(<img src={EmptyStar} alt="Empty Star" />);

  const splitDate = createdDate.split("T");
  const datePart = splitDate[0]; // 날짜 부분
  const timePart = splitDate[1]; // 시간 부분

  const createReview = () => {

  }

  return (
    <MyReviewItemWrapper>
      {writable ? (
        <div className="editable">
          {/* 별점 수정 로직 추가 필요 */}
          <input type='number' />
          <input type="text" />
          <input type="file"  />
        </div>
      ) : (
        <div className="non-editable">
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
            {imageUrl && <img src={imageUrl} alt="Review" />}
          </div>
        </div>
      )}
      {writable && (
        <Button text="등록" size='s' color='Primary' radius='s' handleClick={createReview}/>
      )}
    </MyReviewItemWrapper>
  );
}

export default MyReview;
