import { vendorApi, authorization } from "../index";

const api = vendorApi

export async function join(data: Object, success: any, fail: any) {
    await api.post(`/join`, data).then(success).catch(fail);
}

export async function withdrawal(data: Object, success: any, fail: any) {
    await api.post(`/withdrawal`, data).then(success).catch(fail);
}

export async function getInfo(success: any, fail: any) {
    api.defaults.headers["Authorization"] = authorization
    await api.get(`/info`).then(success).catch(fail);
}

export async function edit(data: Object, success: any, fail: any) {
    api.defaults.headers["Authorization"] = authorization
    await api.put(``, data).then(success).catch(fail);
}

