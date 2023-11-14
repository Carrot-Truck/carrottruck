import { cartApi, getAuthorization } from "./index";

const api = cartApi;

export async function createCart(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(``, data).then(success).catch(fail);
}

export async function getCart(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``).then(success).catch(fail);
}

export async function editCartMenu(cartMenuId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.patch(`/${cartMenuId}`).then(success).catch(fail);
}

export async function removeCartMenu(cartMenuId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.delete(`/${cartMenuId}`).then(success).catch(fail);
}

export async function getCartOrder(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/order`).then(success).catch(fail);
}
