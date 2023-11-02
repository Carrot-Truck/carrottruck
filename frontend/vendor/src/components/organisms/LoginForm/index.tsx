import { useState } from 'react';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
import { FieldSet, LoginFormContainer } from './style';

function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const login = async () => {
    try {
      // const body = {
      //   email,
      //   password
      // };
      // const response = await ;

      // // 로컬스토리지에 토큰 저장
      // localStorage.setItem('token', response.headers.token);
      // localStorage.setItem('memberkey', response.headers.memberkey);

      // // 이벤트를 발생시켜 AuthProvider의 함수를 실행.
      // const memberLoginEvent = new Event('memberLogin');
      // window.dispatchEvent(memberLoginEvent);

      // 홈으로 이동
      navigate('/vendor');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <LoginFormContainer>
      {/* <DownIcon /> */}
      <FieldSet>
        <Input type="text" value={email} setValue={setEmail} placeholder="이메일 아이디" />
        <Input type="password" value={password} setValue={setPassword} placeholder="비밀번호" />
      </FieldSet>
      <Button text="로그인" color="Primary" size="full" radius="s" handleClick={login} />
    </LoginFormContainer>
  );
}

export default LoginForm;
