import React, { useState, useEffect } from 'react';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
// import { certEmailApi, checkEmailApi, joinApi, sendEmailApi } from 'utils/apis/auth';
// import { yearToAge } from 'utils/common/yearToAge';
import { useNavigate } from 'react-router-dom';
import { JoinFormContainer } from './style';

function JoinForm() {
  const navigate = useNavigate();
  const [isDone, setIsDone] = useState(false);
  const [email, setEmail] = useState('');
  const [authNumber, setAuthNumber] = useState('');
  const [isSentCode, setIsSentCode] = useState(false);
  const [isVerified, setIsVerified] = useState(false);
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [name, setName] = useState('');
  const [year, setYear] = useState(0);
  const [nickname, setNickname] = useState('');

  // 회원 가입
  const join = async () => {
    if (isDone) {
      try {
        const body = {
          email,
          password,
          name,
          age: yearToAge(year),
          nickname
        };
        const response = await joinApi(body);

        if (response.status === 201) {
          alert('회원가입이 완료되었습니다!');
          navigate('/auth/login');
        }
        return;
      } catch (error) {
        alert(error);
      }
    }
    if (!email) {
      alert('이메일을 입력해주세요');
    } else if (!isVerified) {
      alert('이메일 인증을 완료해주세요.');
    } else if (!password) {
      alert('비밀번호를 입력해주세요');
    } else if (!passwordConfirm) {
      alert('비밀번호 확인을 입력해주세요');
    } else if (!name) {
      alert('이름을 입력해주세요');
    } else if (!year) {
      alert('나이를 입력해주세요');
    } else if (!nickname) {
      alert('닉네임을 입력해주세요');
    } else {
      alert('모든 정보를 입력해주세요');
    }
  };

  // 이메일 인증 하기
  const verifyEmail = async () => {
    try {
      const certReqBody = {
        email,
        authNumber
      };
      const certRes = await certEmailApi(certReqBody);
      console.log(certRes);
      if (certRes.status === 200) {
        const duplicateRes = await checkEmailApi({ email });
        console.log(duplicateRes);
        if (duplicateRes.status === 200) {
          if (duplicateRes.data.data) {
            throw new Error('이미 사용 중인 이메일입니다.');
          }
          alert('이메일 인증이 완료되었습니다.');
          setIsVerified(true);
        }
      }
    } catch (error) {
      console.log(error);
    }
  };

  // 이메일 인증번호 전송
  const sendCode = async () => {
    try {
      const body = {
        email
      };
      const response = await sendEmailApi(body);
      console.log(response);
      if (response.status === 200) {
        alert('이메일이 전송되었습니다. 이메일 확인 후, 인증번호를 입력하세요.');
        setIsSentCode(true);
      }
    } catch (error) {
      console.log(error);
    }
  };

  // 이메일 인증 버튼 텍스트
  const setEmailBtnText = () => {
    if (isVerified) {
      return '이메일 인증 완료';
    }
    if (isSentCode) {
      return '이메일 인증코드 확인';
    }
    return '이메일 인증코드 전송';
  };

  useEffect(() => {
    if (email && password && passwordConfirm && isVerified && name && year && nickname) {
      setIsDone(true);
    } else {
      setIsDone(false);
    }
  }, [email, password, passwordConfirm, isVerified, name, year, nickname]);

  return (
    <JoinFormContainer>
      <div className="join-form">
        <div className="input">
          <span>이메일 아이디</span>
          <Input
            placeholder="example@example.com"
            value={email}
            setValue={setEmail}
            type="text"
            disabled={isVerified}
          />
          {isSentCode && !isVerified ? (
            <Input placeholder="6자리 인증코드 입력" type="text" value={authNumber} setValue={setAuthNumber} />
          ) : (
            <div />
          )}
          <Button
            color="SubFirst"
            handleClick={isSentCode ? verifyEmail : sendCode}
            radius="m"
            size="full"
            text={setEmailBtnText()}
            disabled={isVerified}
          />
        </div>
        <div className="field">
          <div className="input">
            <span>비밀번호</span>
            <Input placeholder="8자 ~ 16자 비밀번호 입력" value={password} setValue={setPassword} type="password" />
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
          <div className="field-row input">
            <div className="input">
              <span>이름</span>
              <Input placeholder="이름 입력" value={name} setValue={setName} type="text" />
            </div>
            <div className="input">
              <span>출생년도</span>
              <Input placeholder="YYYY" value={year} setValue={setYear} type="number" />
            </div>
          </div>
          <div className="input">
            <span>닉네임</span>
            <Input placeholder="2자 ~ 20자 입력" value={nickname} setValue={setNickname} type="text" />
          </div>
        </div>
      </div>
      <div className="next-btn">
        <Button handleClick={join} color={isDone ? 'Primary' : 'Normal'} size="full" radius="m" text="회원가입 완료" />
      </div>
    </JoinFormContainer>
  );
}

export default JoinForm;
