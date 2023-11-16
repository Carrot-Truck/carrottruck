import Navbar from "components/organisms/Navbar";
import { AnalysisLayout } from "./style";
import Button from "components/atoms/Button";
import { useNavigate } from "react-router";
import NaverMap from "components/atoms/Map";
import { useEffect } from "react";

function AnalysisPage() {
  const navigate = useNavigate();

  const clientId: string = process.env.REACT_APP_CLIENT_ID || "";

  useEffect(() => {
    const eventSource = new EventSource(`${process.env.REACT_APP_API_URL}/order/subscribe/ssafy@ssafy.com`);

    eventSource.addEventListener('sse', (event) => {
      if (event.data === 'connect completed') {
        console.log('SSE 연결 성공함');
        return;
      } else {
        console.log(event);
        // axios
        //   .get(`https://j9c210.p.ssafy.io/api1/alarm/${email}`, {
        //     headers: {
        //       Authorization: `Bearer ${accessToken}`,
        //     },
        //   })
        //   .then((res) => {
        //     console.log('알람 내용 불러오는 거 성공함');
        //     console.log(res.data);
        //     setAlarmData(res.data);
        //   })
        //   .catch((err) => {
        //     console.log('에러..');
        //     console.log(err);
        //   });

        // Swal.fire({
        //   icon: 'success',
        //   title: '새로운 알람이 도착했습니다!',
        //   text: event.data.content,
        //   confirmButtonColor: '#f8a70c',
        // });

        // console.log('sse통해 넘어오는 이벤트 데이터', event);
      }
    });

    // 컴포넌트가 언마운트될 때 SSE 연결을 닫습니다.
    return () => {
      eventSource.close();
    };
  }, []);

  //REACT_APP_CLIENT_ID
  return (
    <AnalysisLayout>
      <NaverMap clientId={clientId} markers={[]} />
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
