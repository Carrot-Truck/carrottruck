import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackSpace from 'components/atoms/BackSpace';
import { AddMenuLayout, Input, P}  from './style';
import Button from 'components/atoms/Button';
import Navbar from '../Navbar';
import ScheduleMap from "components/atoms/ScheduleMap"
import { reverseGeocoding } from 'api/address'
import {  AxiosResponse } from 'axios';
import { createSchedule, editSchedule, getSchedule } from 'api/schedule';

interface ScheduleFormInterface {
  foodTruckId: number,
  dayOfWeek: string,
  startTime: string,
  endTime: string,
  latitude: string,
  longitude: string,
}

function ModifyScheduleForm() {
  // const location = useLocation();
  const navigate = useNavigate();
  const location = useLocation();
  const [address, setAddress] = useState('');
  const [longitude, setLongitude] = useState(location.state.longitude);
  const [latitude, setLatitude] = useState(location.state.latitude);
  const [startTime, setStartTime] = useState(location.state.startTime);
  const [endTime, setEndTime] = useState(location.state.endTime);
  const scheduleId = location.state.scheduleId;
  const [day] = useState(location.state.day);

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

  const validateTimeFormat = (time: string) => {
    // 정규표현식: 'HH:MM' 형식 검증 (00:00 ~ 23:59)
    const regex = /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/;
    return regex.test(time);
  };

  const validateTimeInput = (times : ScheduleFormInterface) =>{
    if(times.startTime !== '' || times.endTime !== '' || times.latitude !== '' || times.longitude !== ''){
      if(validateTimeFormat(times.startTime) && validateTimeFormat(times.endTime) && times.latitude !== '' && times.longitude !== ''){
        return 1; // 입력이 있었고, 두 형식이 모두 유효한 format 이라면 1 반환
      }
      return -1; // 입력이 있었지만 두 형식 중 하나라도 유효하지 않다면 다시 입력해야하므로 -1
    }else{
      return 0; // 아무 입력이 없는 요일이므로 0 반환
    }
  }

  const modifyScheduleDetail = () => {
    // 로직을 추가하여 스케줄 등록 추가
    const inputRequest = {
      foodTruckId: Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0"),
      dayOfWeek: day, // useState에서 반환된 상태 값만 사용
      startTime: startTime,
      endTime: endTime,
      latitude: latitude,
      longitude: longitude
    };
  
    if (validateTimeInput(inputRequest) <= 0) {
      alert("입력이 올바르지 않습니다.");
      return;
    }

    if (location.state.startTime !== null && location.state.startTime !== ''){
      console.log("new location: ", latitude, longitude)
      editSchedule(scheduleId, inputRequest, ()=>{ navigate(-1); return;}, ()=>{alert('등록 중 오류 발생!\n관리자에게 문의하세요'); navigate('/');});
    }else{
      createSchedule(inputRequest, ()=>{ navigate(-1); return;}, ()=>{alert('등록 중 오류 발생!\n관리자에게 문의하세요'); navigate('/');});
    }
    
  };
  const updateLocation = (response: AxiosResponse) =>{
    setLongitude(response.data.data.longitude);
    setLatitude(response.data.data.latitude);
    setAddress(response.data.data.address);
    savedMarker.latitude = response.data.data.latitude;
    savedMarker.longitude = response.data.data.longitude;
  };

  const initialRegistration = () =>{
    setLatitude("37.5665");
    setLongitude("126.9780");
    setAddress("");
  };

  const savedMarker = {
    latitude: latitude,
    longitude: longitude
  };
  
  useEffect(()=>{
    console.log(latitude, longitude)
    const data = {
      foodTruckId: Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0")
    }
    getSchedule(scheduleId, data, updateLocation, initialRegistration);
    
  }, [])

  return (
    <AddMenuLayout>
      <div style={{ flexGrow: 1 }}>
        <div className="header">
          <BackSpace/>
          <p>스케줄 수정</p>
        </div>
        {/* <img className="headerImage" src={menuImageUrl} alt={menuName} /> */}
        <div className="storeInfo">
          <ScheduleMap clientId={CLIENT_KEY} savedMarker={savedMarker} onMarkerChange={handleMarkerChange}></ScheduleMap>
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
