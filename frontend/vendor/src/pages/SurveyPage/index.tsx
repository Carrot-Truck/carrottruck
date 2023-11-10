import Navbar from "components/organisms/Navbar";
import { SurveyLayout } from "./style";
import SelectAddress from "components/organisms/SelectAddress";
import { useState } from "react";
import SurveyList from "components/organisms/SurveyList";

function SurveyPage() {
  const [sidoId, setSidoId] = useState<number | null>(null);
  const [sigunguId, setSigunguId] = useState<number | null>(null);
  const [sidoName, setSidoName] = useState<string>("");
  const [sigunguName, setSigunguName] = useState<string>("");
  const [dongName, setDongName] = useState<string>("");

  return (
    <SurveyLayout>
      {dongName ? (
        <SurveyList sido={sidoName} sigungu={sigunguName} dong={dongName} />
      ) : (
        <SelectAddress
          sidoId={sidoId}
          setSidoId={setSidoId}
          sigunguId={sigunguId}
          setSigunguId={setSigunguId}
          setSidoName={setSidoName}
          setSigunguName={setSigunguName}
          setDongName={setDongName}
        />
      )}
      <Navbar />
    </SurveyLayout>
  );
}

export default SurveyPage;
