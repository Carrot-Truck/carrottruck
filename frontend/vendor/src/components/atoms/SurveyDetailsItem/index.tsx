import { SurveyDetailsItemWrapper } from "./style";

interface ISurveyListItemProps {
  nickname: string;
  content: string;
  createdDate: string;
}

function SurveyDetailsItem({ nickname, content, createdDate }: ISurveyListItemProps) {
  const dateString: string = createdDate.replace("T", " ");

  return (
    <SurveyDetailsItemWrapper>
      <div className="detail-item">
        <div className="detail-first-row">
          <span className="detail-nickname">{nickname}</span>
          <span className="detail-date">{dateString}</span>
        </div>
        <div className="detail-content">{content}</div>
      </div>
    </SurveyDetailsItemWrapper>
  );
}

export default SurveyDetailsItem;
