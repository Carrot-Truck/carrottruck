import { useEffect, useState } from 'react';
import Button from 'components/atoms/Button';
import { FieldSet, LoginFormContainer, P, Input } from './style';
import { createSchedule, getSchedules } from 'api/schedule';
// import { createSchedule, getSchedules } from 'api/schedule';
import { useNavigate } from 'react-router-dom';
import { AxiosError, AxiosResponse } from 'axios';
// import Input from 'components/atoms/Input';
  
interface ScheduleFormInterface {
    foodTruckId: number,
    dayOfWeek: string,
    startTime: string,
    endTime: string,
    latitude: string,
    longitude: string,
}

function ScheduleForm() {
  const navigate = useNavigate();
  const WholeSchedule  = {
    MONDAY: {
      foodTruckId: 0,
      dayOfWeek: 'MONDAY',
      latitude : "37.5665",
      longitude : "126.9780",
      startTime: '',
      endTime: ''
    },
    TUESDAY: {
      foodTruckId: 0,
      dayOfWeek: 'MONDAY',
      latitude : "37.5665",
      longitude : "126.9780",
      startTime: '',
      endTime: ''
      },
      WEDNESDAY: {
        foodTruckId: 0,
        dayOfWeek: 'MONDAY',
        latitude : "37.5665",
        longitude : "126.9780",
        startTime: '',
        endTime: ''
      },
      THURSDAY: {
        foodTruckId: 0,
        dayOfWeek: 'MONDAY',
        latitude : "37.5665",
        longitude : "126.9780",
        startTime: '',
        endTime: ''
      },
      FRIDAY: {
        foodTruckId: 0,
        dayOfWeek: 'MONDAY',
        latitude : "37.5665",
        longitude : "126.9780",
        startTime: '',
        endTime: ''
      },
      SATURDAY: {
        foodTruckId: 0,
        dayOfWeek: 'MONDAY',
        latitude : "37.5665",
        longitude : "126.9780",
        startTime: '',
        endTime: ''
      },
      SUNDAY: {
        foodTruckId: 0,
        dayOfWeek: 'MONDAY',
        latitude : "37.5665",
        longitude : "126.9780",
        startTime: '',
        endTime: ''
      },
  };
  const foodTruckId = Number.parseInt(localStorage.getItem("selectedFoodTruckId") || "0");
  const [schedules, setSchedules] = useState(WholeSchedule);

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
  const modifySchedule = () =>{
    // TODO: 입력된 시간이 시간형식 format 을 지키는지 확인 HH:MM 형식으로 받아야한다. 만족하지 않을 경우 alert를 띄우고 return;

    if(validateTimeInput(schedules.MONDAY) < 0 || validateTimeInput(schedules.TUESDAY) < 0 || validateTimeInput(schedules.WEDNESDAY) < 0 ||
    validateTimeInput(schedules.THURSDAY) < 0 || validateTimeInput(schedules.FRIDAY) < 0 || validateTimeInput(schedules.SATURDAY) < 0 ||
    validateTimeInput(schedules.SUNDAY) < 0 ){
      alert("입력이 올바르지 않습니다.");
      return;
    }
    if(validateTimeInput(schedules.MONDAY) > 0){
      createSchedule(schedules.MONDAY, ()=>{alert("업데이트 완료!"); return;}, (error: AxiosError)=>{console.log("1 스케쥴 업데이트 오류", error); navigate('/'); return;});
    } 
    if( validateTimeInput(schedules.TUESDAY) > 0 ){
      createSchedule(schedules.TUESDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("2 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }
    if(validateTimeInput(schedules.WEDNESDAY) > 0 ){
      createSchedule(schedules.WEDNESDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("3 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }
    if(validateTimeInput(schedules.THURSDAY) > 0){
      createSchedule(schedules.THURSDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("4 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }  
    if( validateTimeInput(schedules.FRIDAY) > 0 ) {
      createSchedule(schedules.FRIDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("5 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }
    if( validateTimeInput(schedules.SATURDAY) > 0 ){
      createSchedule(schedules.SATURDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("6 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }
    if( validateTimeInput(schedules.SUNDAY) > 0 ) {
      createSchedule(schedules.SUNDAY, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("7 스케쥴 업데이트 오류", error); navigate('/'); return;});
    }
    alert("업데이트 완료!")
    // // TODO: 여기에 등록하는 POST 요청 보낼것!
    // Object.entries(schedules).forEach((schedule) => {
    //     console.log("request: ",schedule)
    //     createSchedule(schedule, ()=>{alert("업데이트 완료!")}, (error: AxiosError)=>{console.log("스케쥴 업데이트 오류", error); navigate('/');});
    // });
  };

  const handleFail = (response: AxiosError) =>{
    console.log("스케줄 조회 Error:", response);
    alert("스케줄 조회 중 에러 발생!\n관리자에게 문의하세요.");
    navigate('/');
  }

  const handleSuccess = (response: AxiosResponse) => {
    console.log(response.data);
    
    // setSchedules(fetchedSchedules);
  };

  useEffect (()=>{
    if(foodTruckId === 0){
        alert("푸드트럭을 선택해주세요.")
        navigate('/');
        return;
    }
    const foodTruckIdReq = {
        foodTruckId: foodTruckId
    }
    getSchedules(foodTruckIdReq, handleSuccess, handleFail);
    
  }, []);

  // const setMondayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     MONDAY: {
  //       ...prevSchedules.MONDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setMondayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     MONDAY: {
  //       ...prevSchedules.MONDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setTuesdayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     TUESDAY: {
  //       ...prevSchedules.TUESDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setTuesdayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     TUESDAY: {
  //       ...prevSchedules.TUESDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setWednesdayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     WEDNESDAY: {
  //       ...prevSchedules.WEDNESDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setWednesdayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     WEDNESDAY: {
  //       ...prevSchedules.WEDNESDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setThursdayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     THURSDAY: {
  //       ...prevSchedules.THURSDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setThursdayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     THURSDAY: {
  //       ...prevSchedules.THURSDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setFridayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     FRIDAY: {
  //       ...prevSchedules.FRIDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setFridayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     FRIDAY: {
  //       ...prevSchedules.FRIDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setSaturdayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     SATURDAY: {
  //       ...prevSchedules.SATURDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setSaturdayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     SATURDAY: {
  //       ...prevSchedules.SATURDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };
  // const setSundayStartTime = (startTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     SUNDAY: {
  //       ...prevSchedules.SATURDAY,
  //       startTime: startTime
  //     }
  //   }));
  // };

  // const setSundayEndTime = (endTime: string) => {
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     SUNDAY: {
  //       ...prevSchedules.SUNDAY,
  //       endTime: endTime
  //     }
  //   }));
  // };

  type DayOfWeek = 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY' | 'SATURDAY' | 'SUNDAY';

  // const handleInputChange = (day: DayOfWeek, addressInput: string) =>{
  //   setSchedules(prevSchedules => ({
  //     ...prevSchedules,
  //     [day]: {
  //         ...prevSchedules[day],
  //         address: addressInput
  //     }
  // }));
  // }

  const modifySchedulDeetail = (day: DayOfWeek, startTime: string, endTime: string) =>{
    navigate('/schedule/modify', { state: { day: day, startTime: startTime, endTime: endTime } });
  }

  useEffect (() =>{
    setSchedules(schedules);
  }, [schedules])

    return (
        <LoginFormContainer>
            <FieldSet>
                <P>월</P>
                <Input
                    type="text"
                    value={schedules.MONDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setMondayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.MONDAY.endTime || ''}
                    // onChange={(e) => setMondayEndTime(e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("MONDAY", schedules.MONDAY.startTime, schedules.MONDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>화</P>
                <Input
                    type="text"
                    value={schedules.TUESDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setTuesdayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.TUESDAY.endTime || ''}
                    // onChange={(e) => setTuesdayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("TUESDAY", schedules.TUESDAY.startTime, schedules.TUESDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>수</P>
                <Input
                    type="text"
                    value={schedules.WEDNESDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setWednesdayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.WEDNESDAY.endTime || ''}
                    // onChange={(e) => setWednesdayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("WEDNESDAY", schedules.WEDNESDAY.startTime, schedules.WEDNESDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>목</P>
                <Input
                    type="text"
                    value={schedules.THURSDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setThursdayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.THURSDAY.endTime || ''}
                    // onChange={(e) => setThursdayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("THURSDAY", schedules.THURSDAY.startTime, schedules.THURSDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>금</P>
                <Input
                    type="text"
                    value={schedules.FRIDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setFridayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.FRIDAY.endTime || ''}
                    // onChange={(e) => setFridayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("FRIDAY", schedules.FRIDAY.startTime, schedules.FRIDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>토</P>
                <Input
                    type="text"
                    value={schedules.SATURDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setSaturdayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.SATURDAY.endTime || ''}
                    // onChange={(e) => setSaturdayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("SATURDAY", schedules.SATURDAY.startTime, schedules.SATURDAY.endTime)}
                />
            </FieldSet>
            <FieldSet>
                <P>일</P>
                <Input
                    type="text"
                    value={schedules.SUNDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    // onChange={(e) => setSundayStartTime(e.target.value)}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.SUNDAY.endTime || ''}
                    // onChange={(e) => setSundayEndTime(e.target.value)}
                    // onChange={(e) => handleInputChange(day, 'endTime', e.target.value)}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("SUNDAY", schedules.SUNDAY.startTime, schedules.SUNDAY.endTime)}
                />
            </FieldSet>
            
            <Button text="저장" color="Primary" size="full" radius="s" handleClick={modifySchedule} />
        </LoginFormContainer>
    );
}

export default ScheduleForm;
