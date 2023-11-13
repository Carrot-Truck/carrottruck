import { getAuthorization, vendorInfoApi } from "../index";

const api = vendorInfoApi;

export async function createVendorInfo(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(``, data).then(success).catch(fail);
}

export async function getVendorInfo(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``).then(success).catch(fail);
}

export async function deleteVendorInfo(vendorInfoId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.delete(`/${vendorInfoId}`).then(success).catch(fail);
}
