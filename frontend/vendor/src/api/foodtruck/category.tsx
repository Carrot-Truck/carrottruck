import { categoryApi } from "../index";

const api = categoryApi

export async function getCategories(success: any, fail: any) {
    await api.get(``).then(success).catch(fail);
}
