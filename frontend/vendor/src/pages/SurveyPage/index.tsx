import Navbar from "components/organisms/Navbar";
import { SurveyLayout } from "./style";
import SelectAddress from "components/organisms/SelectAddress";
import { useState } from "react";
import SurveyList from "components/organisms/SurveyList";
import UnselectAddress from "components/atoms/UnselectAddress";
import TitleText from "components/atoms/TitleText";

export function SurveyPage() {
  const [sidoId, setSidoId] = useState<number | null>(null);
  const [sigunguId, setSigunguId] = useState<number | null>(null);
  const [dongId, setDongId] = useState<number | null>(null);
  const [sidoName, setSidoName] = useState<string>("");
  const [sigunguName, setSigunguName] = useState<string>("");
  const [dongName, setDongName] = useState<string>("");
  const [categoryId, setCategoryId] = useState<number | null>(null);

  return (
    <SurveyLayout>
      <UnselectAddress
        sidoId={sidoId}
        setSidoId={setSidoId}
        sigunguId={sigunguId}
        setSigunguId={setSigunguId}
        dongId={dongId}
        setDongId={setDongId}
        categoryId={categoryId}
        setCategoryId={setCategoryId}
      />
      <TitleText text="수요조사" size="l" />
      {dongId ? (
        <SurveyList
          sido={sidoName}
          sigungu={sigunguName}
          dong={dongName}
          categoryId={categoryId}
          setCategoryId={setCategoryId}
        />
      ) : (
        <SelectAddress
          sidoId={sidoId}
          setSidoId={setSidoId}
          sigunguId={sigunguId}
          setSigunguId={setSigunguId}
          dongId={dongId}
          setDongId={setDongId}
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
