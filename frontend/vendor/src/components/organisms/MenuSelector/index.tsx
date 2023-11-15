import { MenuSelectorContainer } from "./style";
import NothingHere from "components/atoms/NothingHere";
import MenuSelectorItem from "components/atoms/MenuSelectorItem";
import { setForSale, setSoldout } from "api/sale";
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
  setMenuItemList: React.Dispatch<React.SetStateAction<IMenuItem[]>>;
  onSale: boolean;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function MenuSelector({ menuItemList, setMenuItemList, onSale }: IMenuSelectorProps) {
  const handleMenuItemClick = (index: number) => {
    const soldout: boolean | undefined = menuItemList.at(index)?.menuSoldOut;
    if (soldout !== undefined) {
      if (!onSale) {
        setMenuItemList((prevMenuList) => {
          return prevMenuList.map((menuItem) => {
            if (menuItem.menuId === menuItemList[index].menuId) {
              return { ...menuItem, menuSoldOut: !menuItem.menuSoldOut };
            }
            return menuItem;
          });
        });
      } else {
        if (
          !menuItemList[index].menuSoldOut &&
          window.confirm(`다음 상품을 품절상태로 변경할까요?\n${menuItemList[index].menuName}`)
        ) {
          setSoldout(
            menuItemList[index].menuId,
            (response: AxiosResponse) => {
              const data = getData(response);
              if (data == menuItemList[index].menuId) {
                setMenuItemList((prevMenuList) => {
                  return prevMenuList.map((menuItem) => {
                    if (menuItem.menuId === data) {
                      return { ...menuItem, menuSoldOut: !menuItem.menuSoldOut };
                    }
                    return menuItem;
                  });
                });
              }
            },
            (error: any) => {
              console.log(error);
            }
          );
        } else if (
          menuItemList[index].menuSoldOut &&
          window.confirm(`다음 상품을 판매가능상태로 변경할까요?\n${menuItemList[index].menuName}`)
        ) {
          setForSale(
            menuItemList[index].menuId,
            (response: AxiosResponse) => {
              const data = getData(response);
              if (data == menuItemList[index].menuId) {
                setMenuItemList((prevMenuList) => {
                  return prevMenuList.map((menuItem) => {
                    if (menuItem.menuId === data) {
                      return { ...menuItem, menuSoldOut: !menuItem.menuSoldOut };
                    }
                    return menuItem;
                  });
                });
              }
            },
            (error: any) => {
              console.log(error);
            }
          );
        }
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
            image={data.menuImageUrl}
            handleItemClick={() => handleMenuItemClick(index)}
            className={`menu-item ${data.menuSoldOut ? "sold-out" : ""}`}
          />
        ))
      )}
    </MenuSelectorContainer>
  );
}

export default MenuSelector;
