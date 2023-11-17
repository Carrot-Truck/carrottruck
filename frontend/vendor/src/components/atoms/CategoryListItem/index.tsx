import { CategoryListItemWrapper, CategoryTextWrapper } from "./style";

interface ICategory {
  categoryId: number;
  categoryName: string;
}

interface ICategortItemProp {
  category: ICategory;
  onCategoryClick: (categoryId: number) => void;
  selected: boolean | null;
}

function CategoryListItem({
  category,
  onCategoryClick,
  selected,
}: ICategortItemProp & {
  onCategoryClick: (categoryId: number) => void;
}) {
  const onClick = () => {
    onCategoryClick(category.categoryId);
  };

  const imagePath = require(`../../../assets/icons/category${category.categoryId}.svg`);

  return (
    <CategoryListItemWrapper onClick={onClick} selected={selected}>
      <img src={imagePath} alt={`Category ${category.categoryId}`} />
      <CategoryTextWrapper>
        <div>{category.categoryName}</div>
      </CategoryTextWrapper>
    </CategoryListItemWrapper>
  );
}

export default CategoryListItem;
