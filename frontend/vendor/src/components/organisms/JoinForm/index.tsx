import { useState, useEffect } from 'react';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
// import { certEmailApi, checkEmailApi, joinApi, sendEmailApi } from 'utils/apis/auth';
// import { yearToAge } from 'utils/common/yearToAge';
// import { useNavigate } from 'react-router-dom';w
import { JoinFormContainer } from './style';

// interface IJoinFormProps {
//   email: string;
//   phoneNumber: string;
//   password: string;
//   passwordConfirm: string;
//   name: string;
//   nickname: string;
// }

function JoinForm() {
  // const navigate = useNavigate();
  const [isDone, setIsDone] = useState(false);
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [name, setName] = useState('');
  const [nickname, setNickname] = useState('');

  // 회원 가입
  const join = async () => {
    if (isDone) {
      try {
        // const body = {
        //   email,
        //   password,
        //   name,
        //   phoneNumber,
        //   nickname
        // };
        // 회원가입 api작성
        // const response = await

        // if (response.status === 201) {
        //   alert('회원가입이 완료되었습니다!');
        // navigate('/vendor/login');
        // }
        return;
        // eslint-disable-next-line no-unreachable
      } catch (error) {
        alert(error);
      }
    }
    if (!email) {
      alert('이메일을 입력해주세요');
    } else if (!phoneNumber) {
      alert('전화번호를 입력해주세요.');
    } else if (!password) {
      alert('비밀번호를 입력해주세요');
    } else if (!passwordConfirm) {
      alert('비밀번호 확인을 입력해주세요');
    } else if (!name) {
      alert('이름을 입력해주세요');
    } else if (!nickname) {
      alert('닉네임을 입력해주세요');
    } else {
      alert('모든 정보를 입력해주세요');
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
        <div className="input">
          <span>이메일 아이디</span>
          <Input placeholder="example@example.com" value={email} setValue={setEmail} type="text" />
        </div>
        <div className="field">
          <span>전화번호</span>
          <Input placeholder="-없이 입력해주세요" value={phoneNumber} setValue={setPhoneNumber} type="text" />
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
        color={isDone ? 'Primary' : 'SubFirst'}
        size="full"
        radius="m"
        text={isDone ? '회원가입' : '작성 중 이세요'}
      />
    </JoinFormContainer>
  );
}

export default JoinForm;
