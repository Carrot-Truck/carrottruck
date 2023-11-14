import { reviewApi, reviewFormApi, getAuthorization } from "./index";

const api = reviewApi;
const formApi = reviewFormApi;

export async function createReview(data: FormData, success: any, fail: any) {
  await formApi.post(``, data).then(success).catch(fail);
}

export async function createComment(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/comment`, data).then(success).catch(fail);
}

export async function getMyReview(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``).then(success).catch(fail);
}

export async function getFoodTruckReview(foodTruckId: number, success: any, fail: any) {
  await api.get(`/${foodTruckId}`).then(success).catch(fail);
}

export async function withdrawal(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.put(`/withdrawal`, data).then(success).catch(fail);
}

export async function report(data: Object, success: any, fail: any) {
  await api.post(`/report`, data).then(success).catch(fail);
}
