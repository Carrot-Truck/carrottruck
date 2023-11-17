import { useEffect, useState } from "react";
import { SurveyListContainer } from "./style";
import Loading from "components/atoms/Loading";
import { getSurveyCount, getSurveyDetails } from "api/survey";
import { AxiosResponse } from "axios";
import SurveyListItem from "components/atoms/SurveyListItem";
import NothingHere from "components/atoms/NothingHere";
import SurveyDetailsItem from "components/atoms/SurveyDetailsItem";

interface ISurveyDetailsItem {
  surveyId: number;
  nickname: string;
  content: string;
  createdTime: string;
}

interface ISurveyListProps {
  sido: string;
  sigungu: string;
  dong: string;
  categoryId: number | null;
  setCategoryId: React.Dispatch<React.SetStateAction<number | null>>;
}

interface ISurveyItem {
  categoryId: number;
  categoryName: string;
  surveyCount: number;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function SurveyList({ sido, sigungu, dong, categoryId, setCategoryId }: ISurveyListProps) {
  const [loading, setLoading] = useState<Boolean>(true);
  const [surveyItemList, setSurveyItemList] = useState<ISurveyItem[]>([]);
  const [surveyDetails, setSurveyDetails] = useState<ISurveyDetailsItem[]>([]);

  const fetchData = async () => {
    getSurveyCount(
      {
        sido: sido,
        sigungu: sigungu,
        dong: dong,
      },
      (response: AxiosResponse) => {
        const data = getData(response);
        setSurveyItemList(data["surveyCounts"]);
        setLoading(false);
      },
      (error: any) => {
        console.log(error);
      }
    );
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
        const data = getData(response);
        setCategoryId(categoryId);
        setSurveyDetails(data["surveyDetails"]);
      },
      (error: any) => {
        console.log(error);
      }
    );
  };

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
      ) : surveyItemList === undefined || surveyItemList.length == 0 ? (
        <NothingHere />
      ) : surveyDetails.length !== 0 && categoryId !== null ? (
        surveyDetails.map((data: ISurveyDetailsItem, index: number) => (
          <SurveyDetailsItem
            key={index}
            nickname={data.nickname}
            content={data.content}
            createdDate={data.createdTime}
          />
        ))
      ) : (
        surveyItemList.map((data: ISurveyItem, index: number) => (
          <SurveyListItem
            key={index}
            rank={index + 1}
            categoryId={data.categoryId}
            categoryName={data.categoryName}
            surveyCount={data.surveyCount}
            handleSurveyItemClick={() => handleSurveyItemClick(data.categoryId)}
          />
        ))
      )}
    </SurveyListContainer>
  );
}

export default SurveyList;
