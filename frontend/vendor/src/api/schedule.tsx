import { getAuthorization, scheduleApi } from "./index";

const api = scheduleApi;

export async function createSchedule(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(``, data).then(success).catch(fail);
}

export async function getSchedules(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``, { params: data }).then(success).catch(fail);
}

export async function getSchedule(scheduleId: number, data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${scheduleId}`, { params: data }).then(success).catch(fail);
}

export async function editSchedule(scheduleId: number, data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.patch(`/${scheduleId}`, data).then(success).catch(fail);
}

export async function deleteSchedule(scheduleId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.delete(`/${scheduleId}`).then(success).catch(fail);
}
