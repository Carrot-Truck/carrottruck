import { clientApi, getAuthorization } from "../index";

const api = clientApi;

export async function join(data: Object, success: any, fail: any) {
  await api.post(`/join`, data).then(success).catch(fail);
}

export async function withdrawal(data: Object, success: any, fail: any) {
  await api.post(`/withdrawal`, data).then(success).catch(fail);
}

export async function info(success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/info`).then(success).catch(fail);
}

export async function edit(data: Object, success: any, fail: any) {
  await api.put(``, data).then(success).catch(fail);
}
