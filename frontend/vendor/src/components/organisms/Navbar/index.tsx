import { NavbarContainer } from './style';
import NavbarItem from 'components/atoms/NavbarItem';

function Navbar() {
  // 네비게이션 바 아이템 배열 플레이스홀더, 아이콘 경로와 텍스트 포함
  // 실제 애플리케이션에서는 실제 SVG 경로와 관련 제목이 될 것임
  const navbarItems = [
    { iconPath: '../../../assets/icons/first-icon.svg', text: '매출통계' },
    { iconPath: '../../../assets/icons/second-icon.svg', text: '스케줄' },
    { iconPath: '../../../assets/icons/third-icon.svg', text: '메인' },
    { iconPath: '../../../assets/icons/fourth-icon.svg', text: '상권분석' },
    { iconPath: '../../../assets/icons/fifth-icon.svg', text: '푸드트럭' }
  ];
  return (
    <NavbarContainer>
      {navbarItems.map((item) => (
        <NavbarItem svg={item.iconPath} text={item.text} />
      ))}
    </NavbarContainer>
  );
}

export default Navbar;
