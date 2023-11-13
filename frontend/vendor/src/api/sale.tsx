import { getAuthorization, saleApi } from "./index";

const api = saleApi;

export async function openSale(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/open`, data).then(success).catch(fail);
}

export async function getProcessingOrders(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/processing/${foodTruckId}`).then(success).catch(fail);
}

export async function getCompleteOrders(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/complete/${foodTruckId}`).then(success).catch(fail);
}

export async function accept(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/accept`, data).then(success).catch(fail);
}

export async function decline(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/decline`, data).then(success).catch(fail);
}

export async function pause(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.put(`/pause/${foodTruckId}`).then(success).catch(fail);
}

export async function restart(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.put(`/restart/${foodTruckId}`).then(success).catch(fail);
}

export async function soldout(menuId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.put(`/soldout/${menuId}`).then(success).catch(fail);
}

export async function closeSale(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.put(`/cloase/${foodTruckId}`).then(success).catch(fail);
}
