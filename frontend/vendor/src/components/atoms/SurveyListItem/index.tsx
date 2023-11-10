import { SurveyItemItemWrapper } from "./style";

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
    <SurveyItemItemWrapper onClick={handleSurveyItemClick}>
      <div>{categoryName}</div>
      <div>{surveyCount}</div>
    </SurveyItemItemWrapper>
  );
}

export default SurveyListItem;
