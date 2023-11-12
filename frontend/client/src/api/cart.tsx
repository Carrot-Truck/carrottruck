import { cartApi } from "./index";

const api = cartApi

export async function createCart(data: Object, success: any, fail: any) {
    await api.post(``, data).then(success).catch(fail);
}

export async function getCart(success: any, fail: any) {
    await api.get(``).then(success).catch(fail);
}

export async function editCartMenu(cartMenuId: string, success: any, fail: any) {
    await api.patch(`/${cartMenuId}`).then(success).catch(fail);
}

export async function removeCartMenu(cartMenuId: string, success: any, fail: any) {
    await api.delete(`/${cartMenuId}`).then(success).catch(fail);
}

export async function getCartOrder(success: any, fail: any) {
    await api.get(`/order`).then(success).catch(fail);
}

