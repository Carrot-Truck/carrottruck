import { vendorInfoApi } from "../index";

const api = vendorInfoApi

export async function createVendorInfo(data: Object, success: any, fail: any) {
    await api.post(``, data).then(success).catch(fail);
}

export async function getVendorInfo(success: any, fail: any) {
    await api.get(``).then(success).catch(fail);
}

export async function deleteVendorInfo(vendorInfoId: number, success: any, fail: any) {
    await api.delete(`/${vendorInfoId}`).then(success).catch(fail);
}
