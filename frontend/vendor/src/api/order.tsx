import { orderApi } from "./index";

const api = orderApi

export async function getOrders(success: any, fail: any) {
    await api.get(``).then(success).catch(fail);
}

export async function deleteOrder(orderId: number, success: any, fail: any) {
    await api.delete(`/${orderId}`).then(success).catch(fail);
}

export async function getOrderByClient(orderId: number, success: any, fail: any) {
    await api.get(`/client/${orderId}`).then(success).catch(fail);
}

export async function getOrderByVendor(orderId: number, success: any, fail: any) {
    await api.get(`/vendor/${orderId}`).then(success).catch(fail);
}

export async function createOrder(data: Object, success: any, fail: any) {
    await api.post(`/create`, data).then(success).catch(fail);
}

export async function cancelOrder(orderId: number, success: any, fail: any) {
    await api.get(`/${orderId}`).then(success).catch(fail);
}
