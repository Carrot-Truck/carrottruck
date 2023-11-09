import { NavbarContainer } from './style';
import NavbarItem from 'components/atoms/NavbarItem';
import { ReactComponent as Salesstatistics } from 'assets/icons/salesstatistics.svg';
import { ReactComponent as Schedule } from 'assets/icons/schedule.svg';
import { ReactComponent as MainPage } from 'assets/icons/mainpage.svg';
import { ReactComponent as Commercialanalysis } from 'assets/icons/commercialanalysis.svg';
import { ReactComponent as Foodtruck } from 'assets/icons/foodtruck.svg';

function Navbar() {
  return (
    <NavbarContainer>
      <NavbarItem svg={Salesstatistics} text="매출통계" to="" />
      <NavbarItem svg={Schedule} text="스케줄" to="" />
      <NavbarItem svg={MainPage} text="메인" to="/" />
      <NavbarItem svg={Commercialanalysis} text="상권분석" to="" />
      <NavbarItem svg={Foodtruck} text="푸드트럭" to="/foodtruck" />
    </NavbarContainer>
  );
}

export default Navbar;
