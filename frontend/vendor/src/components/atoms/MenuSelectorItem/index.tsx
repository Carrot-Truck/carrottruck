import { MenuSelectorItemWrapper } from "./style";

interface IMenuSelectorItemProps {
  name: string;
  price: number;
  description: string;
  soldout: boolean;
  image: string;
  handleItemClick: () => void;
}

function MenuSelectorItem({
  name,
  price,
  description,
  soldout,
  image,
  handleItemClick,
}: IMenuSelectorItemProps) {
  return (
    <MenuSelectorItemWrapper onClick={handleItemClick} soldout={soldout}>
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
