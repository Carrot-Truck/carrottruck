import Navbar from "components/organisms/Navbar";
import { SurveyLayout } from "./style";
import SelectAddress from "components/organisms/SelectAddress";

function SurveyPage() {
  return (
    <SurveyLayout>
      surveyPage
      <SelectAddress sidoId={null} sigunguId={null} />
      <Navbar />
    </SurveyLayout>
  );
}

export default SurveyPage;
