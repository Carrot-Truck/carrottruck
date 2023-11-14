import CategoryListItem from "components/atoms/CategoryListItem";
import { CategoryListContainer } from "./style";

interface ICategory {
  categoryId: number;
  categoryName: string;
}

interface ICategoryProps {
  categories: ICategory[];
  onCategoryClick: (categoryId: number, categoryName: string) => void;
  selectedCategoryId: number | null; // 이 줄을 추가
}

function CategoryList({
  categories,
  onCategoryClick,
  selectedCategoryId
}: ICategoryProps & {
  onCategoryClick: (categoryId: number, categoryName: string) => void;
}) {
  return (
    <CategoryListContainer>
      {categories.map((category) => (
        <CategoryListItem
          key={category.categoryId}
          category={category}
          onCategoryClick={onCategoryClick}
          isSelected={selectedCategoryId === category.categoryId}
        />
      ))}
    </CategoryListContainer>
  );
}

export default CategoryList;
