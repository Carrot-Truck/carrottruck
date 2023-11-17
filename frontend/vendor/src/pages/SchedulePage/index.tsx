import Navbar from "components/organisms/Navbar";
import { SchedulePageLayout } from "./style";
import BackSpace from 'components/atoms/BackSpace';
import ScheduleForm from "components/organisms/ScheduleForm";

function SchedulePage() {
  return (
    <SchedulePageLayout>
      <div className="header">
        <BackSpace></BackSpace>
        <p>스케줄 수정</p>
      </div>
      <ScheduleForm></ScheduleForm>
      <Navbar />
    </SchedulePageLayout>
  );
}

export default SchedulePage;
