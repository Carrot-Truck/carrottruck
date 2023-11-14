import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackSpace from 'components/atoms/BackSpace';
import { AddMenuLayout, Input, P}  from './style';
import Button from 'components/atoms/Button';
import Navbar from '../Navbar';
import ScheduleMap from "components/atoms/ScheduleMap"
import { reverseGeocoding } from 'api/address'
import { AxiosResponse } from 'axios';

function ModifyScheduleForm() {
  // const location = useLocation();
  const navigate = useNavigate();
  const location = useLocation();
  const [address, setAddress] = useState('');
  const [longitude, setLongitude] = useState('');
  const [latitude, setLatitude] = useState('');
  const [startTime, setStartTime] = useState(location.state.startTime);
  const [endTime, setEndTime] = useState(location.state.endTime);
  // const day = useState(location.state.day);

  const CLIENT_KEY: string = process.env.REACT_APP_CLIENT_ID || 'your-default-key-or-handle-error';

  const handleMarkerChange = (newLatitude: any, newLongitude: any) => {
    setLatitude(newLatitude);
    setLongitude(newLongitude);
    const request = {
      latitude: newLatitude,
      longitude: newLongitude
    }
    reverseGeocoding(request, (response: AxiosResponse)=>{if(response.data.data.address === null) {setAddress("지원하지 않는 주소입니다."); return;}setAddress(response.data.data.address);}, () => {setAddress("유효한 곳에 핀을 놓아주세요.")});
  };

  // const handleBack = () => {
  //   navigate(-1);
  // };

  const modifyScheduleDetail = () => {
    // 로직을 추가하여 스케줄 등록 추가
    
    navigate(-1);
  };

  return (
    <AddMenuLayout>
      <div style={{ flexGrow: 1 }}>
        <div className="header">
          <BackSpace/>
          <p>스케줄 수정</p>
        </div>
        {/* <img className="headerImage" src={menuImageUrl} alt={menuName} /> */}
        <div className="storeInfo">

          <ScheduleMap clientId={CLIENT_KEY} onMarkerChange={handleMarkerChange}></ScheduleMap>
          
          <div className="location">
            <p>{address}</p>
          </div>
          <div className="time">
            <Input
                type="text"
                value={startTime || ''}
                // setValue={setStartTime}
                onChange={(e) => setStartTime(e.target.value)}
                placeholder="시작시간"
            />
            <P>~</P>
            <Input
                type="text"
                value={endTime|| ''}
                onChange={(e) => setEndTime(e.target.value)}
                placeholder="종료시간"
            />
          </div>

          {/* <p  className="quantity">총 금액: {menuPrice * quantity}원</p> */} 
          <Button size='full' radius='m' color='Primary' text='저장' handleClick={modifyScheduleDetail}></Button>
        </div>
      </div>
      <Navbar></Navbar>
    </AddMenuLayout>
  );
}

export default ModifyScheduleForm;
