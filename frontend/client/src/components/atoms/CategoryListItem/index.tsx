import { CategoryListItemWrapper, CategoryTextWrapper } from "./style";
import { AxiosResponse } from "axios";

interface ICategory{
    categoryId : number;
    categoryName : string;
}

interface ICategortItemProp {
    category: ICategory;
}

function CategoryListItem({
    category,
}: ICategortItemProp) {
  return (
    <CategoryListItemWrapper>
      <div>{category.categoryId}</div>
      <CategoryTextWrapper>
        <div>{category.categoryName}</div>
      </CategoryTextWrapper>
    </CategoryListItemWrapper>
  );
}

export default CategoryListItem;