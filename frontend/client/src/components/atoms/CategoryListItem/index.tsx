import { CategoryListItemWrapper, CategoryTextWrapper } from "./style";
import { AxiosResponse } from "axios";

interface ICategory {
    categoryId: number;
    categoryName: string;
}

interface ICategortItemProp {
    category: ICategory;
    onCategoryClick: (categoryId: number, categoryName: string) => void;
    isSelected: boolean | null; // 이 줄을 추가
}

function CategoryListItem({
    category,
    onCategoryClick,
    isSelected, 
}: ICategortItemProp & {
  onCategoryClick: (categoryId: number, categoryName: string) => void;
}) {
  const onClick = () => {
    onCategoryClick(category.categoryId, category.categoryName);
  };

  const imagePath = require(`../../../assets/icons/category${category.categoryId}.svg`);

  return (
    <CategoryListItemWrapper onClick={onClick} isSelected={isSelected}>
      <img src={imagePath} alt={`Category ${category.categoryId}`} />
      <CategoryTextWrapper>
        <div>{category.categoryName}</div>
      </CategoryTextWrapper>
    </CategoryListItemWrapper>
  );
}

export default CategoryListItem;
