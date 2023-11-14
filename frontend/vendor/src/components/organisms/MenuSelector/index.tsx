import { MenuSelectorContainer } from "./style";
import NothingHere from "components/atoms/NothingHere";
import MenuSelectorItem from "components/atoms/MenuSelectorItem";
import { setSoldout } from "api/sale";
import { AxiosResponse } from "axios";

interface IMenuItem {
  menuId: number;
  menuName: string;
  menuPrice: number;
  menuDescription: string;
  menuSoldOut: boolean;
  menuImageUrl: string;
}

interface IMenuSelectorProps {
  menuItemList: IMenuItem[];
  onSale: boolean;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function MenuSelector({ menuItemList, onSale }: IMenuSelectorProps) {
  const handleMenuItemClick = (index: number) => {
    const soldout: boolean | undefined = menuItemList.at(index)?.menuSoldOut;
    if (soldout !== undefined) {
      if (!onSale) {
        menuItemList[index].menuSoldOut = !soldout;
      } else {
        setSoldout(
          menuItemList[index].menuId,
          (response: AxiosResponse) => {
            const data = getData(response);
            console.log(data);
            if (data == index) {
              menuItemList[index].menuSoldOut = !soldout;
            }
          },
          (error: any) => {
            console.log(error);
          }
        );
      }
    }
  };

  return (
    <MenuSelectorContainer $onSale={onSale}>
      {menuItemList === undefined || menuItemList.length == 0 ? (
        <NothingHere />
      ) : (
        menuItemList.map((data: IMenuItem, index: number) => (
          <MenuSelectorItem
            key={index}
            name={data.menuName}
            price={data.menuPrice}
            description={data.menuDescription}
            soldout={data.menuSoldOut}
            image={data.menuImageUrl}
            handleItemClick={() => handleMenuItemClick(index)}
          />
        ))
      )}
    </MenuSelectorContainer>
  );
}

export default MenuSelector;
