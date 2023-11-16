import { NavbarContainer } from './style';
import NavbarItem from 'components/atoms/NavbarItem';
import { ReactComponent as Schedule } from 'assets/icons/schedule.svg';
import { ReactComponent as Jjim } from 'assets/icons/jjim.svg';
import { ReactComponent as MainPage } from 'assets/icons/mainpage.svg';
// import { ReactComponent as User } from 'assets/icons/user.svg';
import { ReactComponent as Cart } from 'assets/icons/shopping_cart.svg';

function Navbar() {
  return (
    <NavbarContainer>
      <NavbarItem svg={MainPage} text="홈" to="/" />
      <NavbarItem svg={Schedule} text="수요조사" to="/survey" />
      <NavbarItem svg={Cart} text="장바구니" to="/cart" />
      <NavbarItem svg={Jjim} text="My당트" to="/mypage" />
    </NavbarContainer>
  );
}

export default Navbar;
