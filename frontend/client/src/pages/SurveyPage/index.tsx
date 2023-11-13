import BackSpace from "components/atoms/BackSpace";
import { SurveyLayout } from "./style";
import { AxiosResponse, AxiosError } from "axios";
import CategoryList from "components/organisms/CategoryList";
import { useEffect, useState } from "react";
import { error } from "console";
import { getCategories } from "api/foodtruck/category";

function SurveyPage() {
  const [category, setCategory] = useState<null | {
    categoryCount: 0;
    categories: {
      categoryId: number;
      categoryName: string;
    }[];
  }>(null);

  const getData = (response: AxiosResponse) => {
    return response.data.data;
  };

  useEffect(() => {
    const fetchData = async () => {
      getCategories(
        (response: AxiosResponse) => {
          const data = getData(response);
          console.log(data);
          setCategory(data);
        },
        (error: any) => {
          setCategory(null);
        }
      );
    };
    fetchData();
  }, []);

  return (
    <SurveyLayout>
      <div className="header">
        <BackSpace></BackSpace>
      </div>
      <div>우리 동네에 이런 메뉴가 있으면 좋겠어요!</div>
      <div>원하는 카테고리</div>
      <div>** 선택한 카테고리 **</div>
      <div>
        <CategoryList   categories={category?.categories ?? []}/>
      </div>
      <div>카테고리 이미지 (선택가능)</div>
      <div>상세메뉴 설명</div>
      <textarea placeholder="ex. 된장 삼겹살 메뉴가 있으면 좋겠어요!!"></textarea>
    </SurveyLayout>
  );
}

export default SurveyPage;
