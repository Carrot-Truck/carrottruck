import { NavbarItemWrapper } from './style';

interface INaverMapProps {
  svg: string;
  text: string;
}

function NavbarItem(props: INaverMapProps) {
  const { svg, text } = props;
  return (
    <NavbarItemWrapper>
      <img src={svg} alt="" />
      <span>{text}</span>
    </NavbarItemWrapper>
  );
}

export default NavbarItem;
