import Navbar from "components/organisms/Navbar";
import { AnalysisLayout } from "./style";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router";
import NaverMap from "components/atoms/Map";

function AnalysisPage() {
  const navigate = useNavigate();
  //REACT_APP_CLIENT_ID
  return (
    <AnalysisLayout>
      <NaverMap clientId={""} markers={[]} />
      <Button
        size="m"
        radius="m"
        color="Normal"
        text="수요조사"
        handleClick={() => {
          navigate("/survey");
        }}
      />
      <Navbar />
    </AnalysisLayout>
  );
}

export default AnalysisPage;
