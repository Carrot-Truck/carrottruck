import { MenuSelectorItemWrapper } from "./style";

interface IMenuSelectorItemProps {
  name: string;
  price: number;
  description: string;
  image: string;
  handleItemClick: () => void;
  className: string;
}

function MenuSelectorItem({
  name,
  price,
  description,
  image,
  handleItemClick,
  className,
}: IMenuSelectorItemProps) {
  return (
    <MenuSelectorItemWrapper onClick={handleItemClick} className={className}>
      <div className="menu-item-image">
        <img src={image} />
      </div>
      <div className="menu-item-detail">
        <div className="menu-name">{name}</div>
        <div className="menu-desc">{description}</div>
      </div>
      <div className="menu-price">{price}원</div>
    </MenuSelectorItemWrapper>
  );
}

export default MenuSelectorItem;
