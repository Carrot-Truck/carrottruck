import styled from "styled-components";

export const SurveyLayout = styled.div`
color: #000; // 글자 색상을 검정으로 설정합니다.
  font-family: BMJUA; // 글꼴 설정
  font-weight: 700;

  .header {
    p {
      font-family: BMJUA;
      font-size: 2rem;
      justify-content: center;
    }
  }

  /* 상세 메뉴 설명 타이틀 */
  .detail-title {
    font-size: 1rem; // 글자 크기
    font-weight: bold; // 글자 두께
    margin-bottom: 10px; // 아래쪽 여백
  }

  /* 텍스트 영역 */
  textarea {
    width: 100%; // 너비를 부모 요소에 맞춤
    height: 120px; // 높이 설정
    border: 1px solid #ccc; // 테두리 색상
    padding: 10px; // 내부 여백
    box-sizing: border-box; // 너비에 padding과 border를 포함
    margin-bottom: 20px; // 아래쪽 여백
    font-size: 0.9rem; // 글자 크기
    font-family: inherit; // 상속받은 글꼴 사용
    resize: none;
  }

`;
