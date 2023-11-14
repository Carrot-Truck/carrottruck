import { ReviewListItemWrapper } from './style';

interface IReviewListItemProps {
  reviewId: number;
  nickname: string;
  grade: number;
  content: string;
  imageUrl: string;
}

function ReviewListItem(props: IReviewListItemProps) {
  const { nickname, grade, content, imageUrl } = props;
  return (
    <ReviewListItemWrapper>
      <p>{nickname}</p>
      <p>{grade}</p>
      <p>{content}</p>
      <img src={imageUrl} alt="" />
    </ReviewListItemWrapper>
  );
}

export default ReviewListItem;
