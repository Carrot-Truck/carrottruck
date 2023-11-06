import { Link } from 'react-router-dom';
import { NavbarItemWrapper } from './style';

function NavbarItem({ svg: Svg, text, to }: { svg: any; text: string; to: string }) {
  return (
    <Link to={to}>
      <NavbarItemWrapper>
        <Svg />
        <span>{text}</span>
      </NavbarItemWrapper>
    </Link>
  );
}

export default NavbarItem;
