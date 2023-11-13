import { categoryApi, getAuthorization } from "../index";

const api = categoryApi;

export async function getCategories(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``).then(success).catch(fail);
}
