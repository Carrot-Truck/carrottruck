import BackSpace from "components/atoms/BackSpace";
import { SurveyLayout } from "./style";
import { AxiosResponse, AxiosError } from "axios";
import CategoryList from "components/organisms/CategoryList";
import { useEffect, useState } from "react";
import { error } from "console";
import { getCategories } from "api/foodtruck/category";
import Button from "components/atoms/Button";
import { submitSurvey } from "api/survey";
import { useNavigate } from "react-router-dom";

function SurveyPage() {
  const navigate = useNavigate();
  const [category, setCategory] = useState<null | {
    categoryCount: 0;
    categories: {
      categoryId: number;
      categoryName: string;
    }[];
  }>(null);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [categoryId, setCategoryId] = useState<number | null>(
    null
  );
  const [latitude, setLatitude] = useState(0);
  const [longitude, setLongitude] = useState(0);
  const [content, setContent] = useState<string>("");

  const handleCategoryClick = (categoryId: number, categoryName: string) => {
    setSelectedCategory(categoryName); // 선택된 카테고리 이름을 상태에 저장
    setCategoryId(categoryId);
  };

  const handleCommentChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setContent(event.target.value);
  };

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

    const fetchLocationAndData = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            setLatitude(position.coords.latitude);
            setLongitude(position.coords.longitude);
          },
          (error) => {
            console.error('Geolocation Error:', error);
            alert('위치 정보를 가져오는데 실패했습니다.');
          }
        );
      } else {
        alert('이 브라우저에서는 위치 정보를 사용할 수 없습니다.');
      }
    };
    fetchLocationAndData();
  }, []);

  // 제출 버튼 클릭 핸들러
  const handleSubmit = async () => {
    // 선택한 카테고리 ID와 코멘트가 모두 있는지 확인
    if (categoryId && content) {
      try {
        const requestData = {
          categoryId,
          latitude,
          longitude,
          content,
        };
        // submitSurvey 함수를 호출하여 서버에 데이터 제출
        await submitSurvey(
          requestData,
          (response: AxiosResponse) => {
            const data = getData(response);
            console.log(data.nickname);
            alert("설문이 제출되었습니다")
            navigate('/');
          },
          (error: any) => {
            console.log(requestData);
            console.log(error);
          }
        );
      } catch (error) {
        console.error("설문 제출 중 오류 발생:", error);
        alert("설문 제출에 실패했습니다.");
      }
    } else {
      // 필요한 데이터가 없는 경우 알림
      alert("카테고리를 선택하고 코멘트를 입력해주세요.");
    }
  };

  return (
    <SurveyLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>우리 동네에 이런 메뉴가 있으면 좋겠어요!</p>
      </div>
      <div>원하는 카테고리</div>
      <div>{selectedCategory}</div>
      <div>
        <CategoryList
          categories={category?.categories ?? []}
          onCategoryClick={handleCategoryClick}
          selectedCategoryId={categoryId}
        />
      </div>
      <div className="detail-title">상세메뉴 설명</div>
      <textarea
        placeholder="ex. 된장 삼겹살 메뉴가 있으면 좋겠어요!!"
        value={content}
        onChange={handleCommentChange}
      ></textarea>
      <div></div>
      <Button
        handleClick={handleSubmit}
        // color={isDisabled ? "SubFirst" : "Primary"}
        color="Primary"
        size="m"
        radius="l"
        text="작성완료"
        // disabled={isDisabled}
      />
    </SurveyLayout>
  );
}

export default SurveyPage;
