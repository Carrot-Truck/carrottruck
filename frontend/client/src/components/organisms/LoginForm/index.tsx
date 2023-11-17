import { useEffect, useState } from 'react';
import Input from 'components/atoms/Input';
import Button from 'components/atoms/Button';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setToken, setIsAuthenticated } from 'slices/userSlice/userSlice';
import { FieldSet, LoginFormContainer } from './style';
import axios from 'axios';

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const accessToken = localStorage.getItem('accessToken');
  const grantType = localStorage.getItem('grantType');
  const APPLICATION_SPRING_SERVER_URL =
    process.env.NODE_ENV === 'production' ? 'https://k9c211.p.ssafy.io/api' : 'http://localhost:8001/api';

  const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      login();
    }
  };

  useEffect(() => {
    const isValidUser = async () => {
      try {
        await axios.get(`${APPLICATION_SPRING_SERVER_URL}/member/client/info`, {
          headers: {
            Authorization: `${grantType} ${accessToken}`
          }
        });
        return true;
      } catch (error) {
        return false;
      }
    };
    isValidUser().then((isValid) => {
      if (isValid) {
        navigate('/');
      }
    });
  }, []);

  const login = async () => {
    try {
      const body = {
        email,
        password
      };
      const response = await axios.post(`${APPLICATION_SPRING_SERVER_URL}/auth/login/client`, body);
      dispatch(setIsAuthenticated(true));
      dispatch(setToken(response.data.data.accessToken));
      // 로컬스토리지에 토큰 저장
      localStorage.setItem('accessToken', response.data.data.accessToken);
      localStorage.setItem('refreshToken', response.data.data.refreshToken);
      localStorage.setItem('grantType', response.data.data.grantType);

      // 이벤트를 발생시켜 AuthProvider의 함수를 실행.
      const memberLoginEvent = new Event('memberLogin');
      window.dispatchEvent(memberLoginEvent);
      alert('로그인 성공!!');
      // 홈으로 이동
      navigate('/');
    } catch (error) {
      setShowErrorMessage(true);
    }
  };

  return (
    <LoginFormContainer>
      {/* <DownIcon /> */}
      <FieldSet>
        <Input
          type="text"
          value={email}
          setValue={setEmail}
          placeholder="이메일 아이디"
          onKeyPress={handleKeyPress} // 엔터 키 이벤트 추가
        />
        <Input
          type="password"
          value={password}
          setValue={setPassword}
          placeholder="비밀번호"
          onKeyPress={handleKeyPress} // 엔터 키 이벤트 추가
        />
      </FieldSet>
      <Button text="로그인" color="Primary" size="full" radius="s" handleClick={login} />
      {showErrorMessage && <p style={{ color: 'red' }}>아이디/패스워드를 확인하세요</p>}
    </LoginFormContainer>
  );
}

export default LoginForm;
