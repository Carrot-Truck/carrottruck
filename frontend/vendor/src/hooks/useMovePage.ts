import { useNavigate } from 'react-router-dom';

// 뒤로가기
function useMovePage() {
  const navigate = useNavigate();

  return [navigate, () => navigate(-1)];
}

export default useMovePage;
