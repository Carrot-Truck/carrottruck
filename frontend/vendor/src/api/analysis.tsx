import { analysisApi } from "./index";

const api = analysisApi

export async function getStoreAnalysis(categoryId: number, success: any, fail: any) {
    await api.get(`/store/${categoryId}`).then(success).catch(fail);
}
