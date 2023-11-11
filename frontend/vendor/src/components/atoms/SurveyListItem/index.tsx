import { SurveyListItemWrapper } from "./style";

interface ISurveyListItemProps {
  categoryName: string;
  surveyCount: number;
  handleSurveyItemClick: () => void;
}

function SurveyListItem({
  categoryName,
  surveyCount,
  handleSurveyItemClick,
}: ISurveyListItemProps) {
  return (
    <SurveyListItemWrapper onClick={handleSurveyItemClick}>
      <div>
        <div>{categoryName}</div>
        <div>{surveyCount}</div>
      </div>
    </SurveyListItemWrapper>
  );
}

export default SurveyListItem;
