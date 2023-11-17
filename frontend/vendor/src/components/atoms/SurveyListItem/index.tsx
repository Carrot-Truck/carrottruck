import { SurveyListItemWrapper } from "./style";

interface ISurveyListItemProps {
  rank: number;
  categoryId: number;
  categoryName: string;
  surveyCount: number;
  handleSurveyItemClick: () => void;
}

const rankMapper: Record<number, string> = {
  1: "gold",
  2: "silver",
  3: "bronze",
};

function SurveyListItem({
  rank,
  categoryId,
  categoryName,
  surveyCount,
  handleSurveyItemClick,
}: ISurveyListItemProps) {
  console.log(rank);

  const imagePath = require(`../../../assets/icons/category${categoryId}.svg`);
  const medal = rank <= 3 ? require(`../../../assets/icons/${rankMapper[rank]}-medal.svg`) : "";

  return (
    <SurveyListItemWrapper onClick={handleSurveyItemClick}>
      <div className="survey-image-wrapper">
        <img src={imagePath} alt={`Category ${categoryId}`} />
      </div>
      <div className="survey-text">{categoryName}</div>
      <div className="survey-text">{surveyCount} 건</div>
      <div className="survey-image-wrapper">
        {medal !== "" ? <img src={medal} alt={`Rank ${rank}`} /> : ""}
      </div>
    </SurveyListItemWrapper>
  );
}

export default SurveyListItem;
