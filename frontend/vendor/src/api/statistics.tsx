import { getAuthorization, statisticsApi } from "./index";

const api = statisticsApi;

export async function getStatisticsBySales(
  foodTruckId: number,
  data: Object,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/sales`, { params: data }).then(success).catch(fail);
}

export async function getStatisticsBySalesDetails(
  foodTruckId: number,
  salesId: number,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/sales-detail/${salesId}`).then(success).catch(fail);
}

export async function getStatisticsByWeek(
  foodTruckId: number,
  data: Object,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/weekly`, { params: data }).then(success).catch(fail);
}

export async function getStatisticsByWeekDetails(
  foodTruckId: number,
  data: Object,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/weekly-detail`, { params: data }).then(success).catch(fail);
}

export async function getStatisticsByMonth(
  foodTruckId: number,
  data: Object,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/monthly`, { params: data }).then(success).catch(fail);
}

export async function getStatisticsByMonthDetails(
  foodTruckId: number,
  data: Object,
  success: any,
  fail: any
) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/${foodTruckId}/monthly-detail`, { params: data }).then(success).catch(fail);
}
