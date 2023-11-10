import Navbar from "components/organisms/Navbar";
import { AnalysisLayout } from "./style";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router";

function AnalysisPage() {
  const navigate = useNavigate();

  return (
    <AnalysisLayout>
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
