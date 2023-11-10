import { useEffect, useState } from "react";
import { SurveyListContainer } from "./style";
import Loading from "components/atoms/Loading";
import { getSurveyCount, getSurveyDetails } from "api/survey";
import { AxiosResponse } from "axios";
import SurveyListItem from "components/atoms/SurveyListItem";

interface ISurveyListProps {
  sido: string;
  sigungu: string;
  dong: string;
}

interface ISurveyItem {
  categoryId: number;
  categoryName: string;
  surveyCount: number;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function SurveyList({ sido, sigungu, dong }: ISurveyListProps) {
  const [loading, setLoading] = useState<Boolean>(true);
  const [surveyItemList, setSurveyItemList] = useState<ISurveyItem[]>([]);

  const fetchData = async () => {
    getSurveyCount(
      {
        sido: sido,
        sigungu: sigungu,
        dong: dong,
      },
      (response: AxiosResponse) => {
        const data = getData(response);
        setSurveyItemList(data);
        console.log("SurveyList", data);
      },
      (error: any) => {
        console.log(error);
      }
    );
    setLoading(false);
  };

  const handleSurveyItemClick = (categoryId: number) => {
    getSurveyDetails(
      categoryId,
      {
        sido: sido,
        sigungu: sigungu,
        dong: dong,
      },
      (response: AxiosResponse) => {
        console.log(response);
      },
      (error: any) => {
        console.log(error);
      }
    );

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    setLoading(true);
    setSurveyItemList([]);
    fetchData();
  }, [dong]);

  return (
    <SurveyListContainer>
      {loading ? (
        <Loading />
      ) : (
        surveyItemList.map((data: ISurveyItem) => (
          <SurveyListItem
            categoryName={data.categoryName}
            surveyCount={data.surveyCount} handleSurveyItemClick={handleSurveyItemClick}          />
        ))
      )}
    </SurveyListContainer>
  );
}

export default SurveyList;
