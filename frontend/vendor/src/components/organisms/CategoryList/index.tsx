import CategoryListItem from "components/atoms/CategoryListItem";
import { CategoryListContainer } from "./style";

interface ICategory {
  categoryId: number;
  categoryName: string;
}

interface ICategoryProps {
  categories: ICategory[];
  onCategoryClick: (categoryId: number) => void;
  selectedCategoryId: number | null;
}

function CategoryList({
  categories,
  onCategoryClick,
  selectedCategoryId,
}: ICategoryProps & {
  onCategoryClick: (categoryId: number) => void;
}) {
  return (
    <CategoryListContainer>
      {categories.map((category) => (
        <CategoryListItem
          key={category.categoryId}
          category={category}
          onCategoryClick={onCategoryClick}
          selected={selectedCategoryId === category.categoryId}
        />
      ))}
    </CategoryListContainer>
  );
}

export default CategoryList;
