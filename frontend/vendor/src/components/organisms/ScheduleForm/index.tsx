import { useEffect, useState } from 'react';
import Button from 'components/atoms/Button';
import { FieldSet, LoginFormContainer, P, Input } from './style';
import {  getSchedules } from 'api/schedule';
import { useNavigate } from 'react-router-dom';
import { AxiosError, AxiosResponse } from 'axios';

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

  const handleFail = (response: AxiosError) =>{
    console.log("스케줄 조회 Error:", response);
    alert("스케줄 조회 중 에러 발생!\n관리자에게 문의하세요.");
    navigate('/');
  }

  const handleSuccess = (response: AxiosResponse) => {
    console.log(response.data);
    
    const newSchedules = {...schedules};

    response.data.data.schedules.forEach((element: any) => {
        const day = element.dayOfWeek as keyof typeof schedules;
        if (newSchedules[day]) {
            newSchedules[day].startTime = element.startTime;
            newSchedules[day].endTime = element.endTime;
            newSchedules[day].foodTruckId = element.scheduleId;
        }
    });

    setSchedules(newSchedules);
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

  type DayOfWeek = 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY' | 'SATURDAY' | 'SUNDAY';

  const modifySchedulDeetail = (day: DayOfWeek, startTime: string, endTime: string, scheduleId: number, lat: string, long: string) =>{
    navigate('/schedule/modify', { state: { day: day, startTime: startTime, endTime: endTime, scheduleId: scheduleId, latitude: lat, longitude: long } });
  }

    return (
        <LoginFormContainer>
            <FieldSet>
                <P>월</P>
                <Input
                    type="text"
                    value={schedules.MONDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.MONDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("MONDAY", schedules.MONDAY.startTime, schedules.MONDAY.endTime, schedules.MONDAY.foodTruckId, schedules.MONDAY.latitude, schedules.MONDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>화</P>
                <Input
                    type="text"
                    value={schedules.TUESDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.TUESDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("TUESDAY", schedules.TUESDAY.startTime, schedules.TUESDAY.endTime, schedules.TUESDAY.foodTruckId, schedules.TUESDAY.latitude, schedules.TUESDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>수</P>
                <Input
                    type="text"
                    value={schedules.WEDNESDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.WEDNESDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("WEDNESDAY", schedules.WEDNESDAY.startTime, schedules.WEDNESDAY.endTime, schedules.WEDNESDAY.foodTruckId, schedules.WEDNESDAY.latitude, schedules.WEDNESDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>목</P>
                <Input
                    type="text"
                    value={schedules.THURSDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.THURSDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("THURSDAY", schedules.THURSDAY.startTime, schedules.THURSDAY.endTime, schedules.THURSDAY.foodTruckId, schedules.THURSDAY.latitude, schedules.THURSDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>금</P>
                <Input
                    type="text"
                    value={schedules.FRIDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.FRIDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("FRIDAY", schedules.FRIDAY.startTime, schedules.FRIDAY.endTime, schedules.FRIDAY.foodTruckId, schedules.FRIDAY.latitude, schedules.FRIDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>토</P>
                <Input
                    type="text"
                    value={schedules.SATURDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.SATURDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("SATURDAY", schedules.SATURDAY.startTime, schedules.SATURDAY.endTime, schedules.SATURDAY.foodTruckId, schedules.SATURDAY.latitude, schedules.SATURDAY.longitude)}
                />
            </FieldSet>
            <FieldSet>
                <P>일</P>
                <Input
                    type="text"
                    value={schedules.SUNDAY.startTime || ''}
                    // setValue={setMondayStartTime}
                    onChange={() => null}
                    placeholder="시작시간"
                />
                <P>~</P>
                <Input
                    type="text"
                    value={schedules.SUNDAY.endTime || ''}
                    onChange={() => null}
                    placeholder="종료시간"
                />
                <Button
                    text="변경"
                    size="s"
                    color='Primary'
                    radius='l'
                    handleClick={()=>modifySchedulDeetail("SUNDAY", schedules.SUNDAY.startTime, schedules.SUNDAY.endTime, schedules.SUNDAY.foodTruckId, schedules.SUNDAY.latitude, schedules.SUNDAY.longitude)}
                />
            </FieldSet>
            {/* <Button text="저장" color="Primary" size="full" radius="s" handleClick={modifySchedule} /> */}
        </LoginFormContainer>
    );
}

export default ScheduleForm;
