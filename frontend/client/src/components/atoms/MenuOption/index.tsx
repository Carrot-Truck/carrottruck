import { MenuOptionWrapper } from './style';
import Checked from 'assets/icons/checked.svg';
import Unckecked from 'assets/icons/unchecked.svg';

interface MenuOptionProps {
  menuOptionId: number;
  menuOptionName: string;
  menuOptionPrice: number;
  menuOptionDescription: string;
  menuOptionSoldOut: boolean;
  isChecked: boolean; // isChecked 속성 추가
  onToggle: () => void; // onToggle 함수 타입 추가
}

function MenuOption(props: MenuOptionProps) {
  const {
    menuOptionId,
    menuOptionName,
    menuOptionPrice,
    menuOptionDescription,
    menuOptionSoldOut,
    isChecked,
    onToggle
  } = props;

  return (
    <>
      {!menuOptionSoldOut && (
        <MenuOptionWrapper>
          <div className="name" onClick={onToggle}>
            <p>{menuOptionName}</p>
            <div className="checked">
              <p>+ {menuOptionPrice}원</p>
              {isChecked ? (
                <img src={Checked} alt="Checked" id={`checkbox-${menuOptionId}`} />
              ) : (
                <img src={Unckecked} alt="Unchecked" id={`checkbox-${menuOptionId}`} />
              )}
              <label htmlFor={`checkbox-${menuOptionId}`}></label>
            </div>
          </div>
          <p>{menuOptionDescription}</p>
        </MenuOptionWrapper>
      )}
    </>
  );
}

export default MenuOption;
