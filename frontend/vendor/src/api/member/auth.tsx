import { authApi } from "../index";

const api = authApi

export async function loginClient(data: Object, success: any, fail: any) {
    await api.post(`/login/client`, data).then(success).catch(fail);
}

export async function loginVendor(data: Object, success: any, fail: any) {
    await api.post(`/login/vendor`, data).then(success).catch(fail);
}

export async function checkEmail(data: Object, success: any, fail: any) {
    await api.post(`/duplicata/email`, data).then(success).catch(fail);
}

export async function authEmail(data: Object, success: any, fail: any) {
    await api.post(`/email`, data).then(success).catch(fail);
}

export async function checkAuthEmail(data: Object, success: any, fail: any) {
    await api.post(`/email/check`, data).then(success).catch(fail);
}
