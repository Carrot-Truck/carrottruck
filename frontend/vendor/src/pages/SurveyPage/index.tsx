import Navbar from "components/organisms/Navbar";
import { SurveyLayout } from "./style";
import SelectAddress from "components/organisms/SelectAddress";
import { useState } from "react";

function SurveyPage() {
  const [sidoId, setSidoId] = useState<number | null>(null);
  const [sigunguId, setSigunguId] = useState<number | null>(null);
  const [dongId, setDongId] = useState<number | null>(null);

  return (
    <SurveyLayout>
      <SelectAddress
        sidoId={sidoId}
        setSidoId={setSidoId}
        sigunguId={sigunguId}
        setSigunguId={setSigunguId}
        dongId={dongId}
        setDongId={setDongId}
      />
      <Navbar />
    </SurveyLayout>
  );
}

export default SurveyPage;
