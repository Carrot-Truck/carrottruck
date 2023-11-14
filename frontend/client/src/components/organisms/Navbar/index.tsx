import { NavbarContainer } from './style';
import NavbarItem from 'components/atoms/NavbarItem';
import { ReactComponent as Schedule } from 'assets/icons/schedule.svg';
import { ReactComponent as Jjim } from 'assets/icons/jjim.svg';
import { ReactComponent as MainPage } from 'assets/icons/mainpage.svg';
import { ReactComponent as User } from 'assets/icons/user.svg';

function Navbar() {
  return (
    <NavbarContainer>
      <NavbarItem svg={MainPage} text="홈" to="/" />
      <NavbarItem svg={Jjim} text="즐겨찾기" to="" />
      <NavbarItem svg={Schedule} text="주문내역" to="" />
      <NavbarItem svg={User} text="My당트" to="/mypage/detail" />
    </NavbarContainer>
  );
}

export default Navbar;
