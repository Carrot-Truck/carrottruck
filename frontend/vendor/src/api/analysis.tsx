import { analysisApi } from "./index";

const api = analysisApi;

export async function getStoreAnalysis(categoryId: number, data: Object, success: any, fail: any) {
  await api.get(`/store/${categoryId}`, { params: data }).then(success).catch(fail);
}
