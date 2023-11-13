import CategoryListItem from "components/atoms/CategoryListItem";
import { CategoryListContainer } from "./style";

interface ICategory {
  categoryId: number;
  categoryName: string;
}

interface ICategoryProps {
  categories: ICategory[];
}

function CategoryList({ categories }: ICategoryProps) {
  return (
    <CategoryListContainer>
      {categories.map((category) => (
        <CategoryListItem key={category.categoryId} category={category} />
      ))}
    </CategoryListContainer>
  );
}

export default CategoryList;
