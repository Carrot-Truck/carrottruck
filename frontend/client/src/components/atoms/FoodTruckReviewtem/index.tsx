import {InfoItemContainer} from './style';

// FoodTruckInfo 컴포넌트에서 사용할 props 인터페이스
interface FoodTruckReviewProps {
  reviewId: number,
  nickname: string,
  grade: number,
  content: string,
  imageUrl: string
}

  const FoodTruckReviewItem = ({ reviewId, nickname, grade, content, imageUrl }: FoodTruckReviewProps) => {

    // const renderValue = (value: any) => {
    //     if (value === null || value === '' || (typeof value === 'object' && Object.keys(value).length === 0)) {
    //       return "정보가 없습니다";
    //     }
    //     return value;
    //   };
      
    return (
      <InfoItemContainer>
        <div>{nickname}</div>
        <div>{grade}</div>
        <div>{content}</div>
        <img src={imageUrl} alt="리뷰사진"/>
      </InfoItemContainer>
    );
  };
  
  export default FoodTruckReviewItem;
  