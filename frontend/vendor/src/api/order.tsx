import { getAuthorization, orderApi, orderEventApi } from "./index";

const api = orderApi;
const eventApi = orderEventApi;

export async function getOrders(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``).then(success).catch(fail);
}

export async function deleteOrder(orderId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.delete(`/${orderId}`).then(success).catch(fail);
}

export async function getOrderByClient(orderId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/client/${orderId}`).then(success).catch(fail);
}

export async function getOrderByVendor(orderId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/vendor/${orderId}`).then(success).catch(fail);
}

export async function createOrder(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/create`, data).then(success).catch(fail);
}

export async function cancelOrder(orderId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${orderId}`).then(success).catch(fail);
}

export async function subscribe(orderId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await eventApi.get(`/${orderId}`).then(success).catch(fail);
}
