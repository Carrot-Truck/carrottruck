import { InfoItemContainer } from './style';
import styled from 'styled-components';
import StarIcon from '../../../assets/icons/star.svg'; // 채워진 별 아이콘
import EmptyStarIcon from '../../../assets/icons/star_empty.svg'; // 빈 별 아이콘

const StarRating = styled.div<{ grade: number }>`
  display: inline-block;
  .star {
    display: inline;
    width: 16px; // 별 아이콘의 크기를 조정하려면 이 값을 변경하세요.
    height: auto;
  }
`;


// 이미지가 유효할 때만 표시하기 위한 컴포넌트
const ReviewImage = styled.img`
  display: ${props => props.src ? 'inline' : 'none'};
  max-width: 100px; // 이미지 크기를 조정하려면 이 값을 변경하세요.
  height: auto;
`;

interface FoodTruckReviewProps {
  reviewId: number;
  nickname: string;
  grade: number;
  content: string;
  imageUrl: string;
}

const FoodTruckReviewItem = ({ nickname, grade, content, imageUrl,}: FoodTruckReviewProps) => {
  const fullStars = new Array(grade).fill(StarIcon);
  const emptyStars = new Array(5 - grade).fill(EmptyStarIcon);

  return (
    <InfoItemContainer>
      <div className='lable'>{nickname}</div>
      <StarRating grade={grade}>
        {fullStars.map((_, index) => (
          <img key={`full-${index}`} src={StarIcon} alt="Full Star" className="star" />
        ))}
        {emptyStars.map((_, index) => (
          <img key={`empty-${index}`} src={EmptyStarIcon} alt="Empty Star" className="star" />
        ))}
      </StarRating>

      <div className='full'>{content}</div>
      {imageUrl && imageUrl !== '' && (<ReviewImage className='full' src={imageUrl} alt="리뷰사진" />)}
    </InfoItemContainer>
  );
};

export default FoodTruckReviewItem;