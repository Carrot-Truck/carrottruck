import { useState, useEffect } from 'react';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
import { JoinFormContainer } from './style';
import axios from 'axios';

function JoinForm() {
  const navigate = useNavigate();
  const [isDone, setIsDone] = useState(false);
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');
  const role = 'VENDOR';
  const [isEmailValid, setIsEmailValid] = useState(false); // 이메일 유효성 상태
  const [isEmailAvailable, setIsEmailAvailable] = useState(false); // 이메일 중복검사 상태
  // const [isEmailVerified, setIsEmailVerified] = useState(false); // 이메일 인증 상태
  const APPLICATION_SPRING_SERVER_URL =
    process.env.NODE_ENV === 'production' ? 'https://k9c211.p.ssafy.io/api' : 'http://localhost:8001/api';

  // 정규식 패턴
  const emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
  // 비밀번호 유효성 검사 함수
  const isPasswordValid = (password: string) => {
    const passwordRegExp = /^(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,16}$/;
    return passwordRegExp.test(password);
  };

  // 이메일 입력값이 변경될 때 유효성 검사 수행
  useEffect(() => {
    if (emailRegExp.test(email)) {
      setIsEmailValid(true);
    } else {
      setIsEmailValid(false);
    }
  }, [email]);

  // 이메일 중복검사
  const checkEmailAvailability = async () => {
    try {
      const body = {
        email
      };
      const response = await axios.post(`${APPLICATION_SPRING_SERVER_URL}/auth/duplication/email`, body);
      console.log(response.data.data);
      if (response.data.data) {
        alert('이미 사용 중인 이메일입니다.');
        setIsEmailAvailable(false);
      } else {
        alert('사용 가능한 이메일입니다.');
        setIsEmailAvailable(true);
      }
    } catch (error) {
      console.error('Error checking email availability', error);
      setIsEmailAvailable(false);
    }
  };

  // 이메일 인증 요청
  // const requestEmailVerification = async () => {
  //   try {
  //     // 이메일 인증 요청 API 호출
  //     // ...

  //     // 인증 이메일을 사용자 이메일 주소로 보냈습니다.
  //     setIsEmailVerified(true);
  //     alert('이메일로 인증 번호가 전송되었습니다.');
  //   } catch (error) {
  //     console.error('Error requesting email verification', error);
  //     setIsEmailVerified(false);
  //   }
  // };

  // 회원 가입
  const join = async () => {
    if (isDone && isEmailAvailable) {
      if (isPasswordValid(password)) {
        // 비밀번호 유효성 검사 추가
        try {
          const body = {
            email,
            password,
            name,
            phoneNumber,
            nickname,
            role
          };
          // 회원가입 API 호출
          const response = await axios.post(`${APPLICATION_SPRING_SERVER_URL}/member/vendor/join`, body);

          if (response.status === 201) {
            const loginResponse = await axios.post(`${APPLICATION_SPRING_SERVER_URL}/auth/login/vendor`, body);

            localStorage.setItem('accessToken', loginResponse.data.data.accessToken);
            localStorage.setItem('refreshToken', loginResponse.data.data.refreshToken);
            localStorage.setItem('grantType', loginResponse.data.data.grantType);

            // 이벤트를 발생시켜 AuthProvider의 함수를 실행.
            const memberLoginEvent = new Event('memberLogin');
            window.dispatchEvent(memberLoginEvent);
            alert('회원가입이 완료되었습니다!');
            navigate('/');
          }
          return;
        } catch (error) {
          console.error('Error during registration', error);
        }
      } else {
        alert('비밀번호는 8~16자이고, 특수문자 1개를 포함해야 합니다.');
      }
    } else {
      if (!email) {
        alert('이메일을 입력해주세요');
      } else if (!isEmailValid) {
        alert('유효한 이메일 형식이 아닙니다.');
      } else if (!isEmailAvailable) {
        alert('이미 사용 중인 이메일입니다.');
        // } else if (!isEmailVerified) {
        //   alert('이메일 인증이 완료되지 않았습니다.');
        // }
      } else if (!phoneNumber) {
        alert('전화번호를 입력해주세요.');
      } else if (!password) {
        alert('비밀번호를 입력해주세요');
      } else if (!passwordConfirm) {
        alert('비밀번호 확인을 입력해주세요');
      } else if (password !== passwordConfirm) {
        alert('비밀번호 확인 불일치!');
      } else if (!name) {
        alert('이름을 입력해주세요');
      } else if (!nickname) {
        alert('닉네임을 입력해주세요');
      }
    }
  };

  useEffect(() => {
    if (email && phoneNumber && password && passwordConfirm && name && nickname) {
      setIsDone(true);
    } else {
      setIsDone(false);
    }
  }, [email, phoneNumber, password, passwordConfirm, name, nickname]);

  return (
    <JoinFormContainer>
      <div className="join-form">
        <div className="inputButton">
          <span>이메일 아이디</span>
          <Input placeholder="example@example.com" value={email} setValue={setEmail} type="text" />
          {/* 유효성에 따라 버튼 활성화/비활성화 */}
          <Button
            color="Primary"
            size="full"
            radius="m"
            text="중복검사"
            handleClick={checkEmailAvailability}
            disabled={!isEmailValid}
          />
          {/* <button onClick={requestEmailVerification} disabled={!isEmailAvailable}>
            인증받기
          </button> */}
        </div>
        <div className="field">
          <span>전화번호</span>
          <Input placeholder="-없이 입력해주세요" value={phoneNumber} setValue={setPhoneNumber} type="text" />
        </div>
        <div className="field">
          <div className="input">
            <span>비밀번호</span>
            <Input placeholder="8자 ~ 16자, 특수문자 포함" value={password} setValue={setPassword} type="password" />
          </div>
          <div className="input">
            <span>비밀번호 확인</span>
            <Input
              placeholder="비밀번호를 한번 더 입력해주세요"
              value={passwordConfirm}
              setValue={setPasswordConfirm}
              type="password"
            />
          </div>
        </div>
        <div className="field">
          <div className="field-row">
            <div className="input">
              <span>이름</span>
              <Input placeholder="이름 입력" value={name} setValue={setName} type="text" />
            </div>
          </div>
          <div className="input">
            <span>닉네임</span>
            <Input placeholder="2자 ~ 10자 입력" value={nickname} setValue={setNickname} type="text" />
          </div>
        </div>
      </div>
      <Button
        handleClick={join}
        color={isDone && isEmailAvailable ? 'Primary' : 'SubFirst'}
        size="full"
        radius="m"
        text={isDone && isEmailAvailable ? '회원가입' : '모든 칸을 입력하세요'}
        disabled={!isDone || !isEmailAvailable}
      />
    </JoinFormContainer>
  );
}

export default JoinForm;
