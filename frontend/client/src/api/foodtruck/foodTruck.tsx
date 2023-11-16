import { foodTruckApi, foodTruckFormApi, getAuthorization } from "../index";

const api = foodTruckApi;
const formApi = foodTruckFormApi;

export async function createFoodTruck(data: FormData, success: any, fail: any) {
  formApi.defaults.headers["Authorization"] = getAuthorization();
  await formApi.post(`/vendor`, data).then(success).catch(fail);
}

export async function getFoodTruckMarkers(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/marker`, { params: data }).then(success).catch(fail);
}

export async function getSearchedFoodTrucks(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(``, { params: data }).then(success).catch(fail);
}

export async function getFoodTruckOverviews(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/overview`, { params: data }).then(success).catch(fail);
}

export async function getFoodTruckDetails(data: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api
    .get(`/vendor/${data}`, { params: { foodTruckId: data } })
    .then(success)
    .catch(fail);
}

export async function getFoodTruck(foodTruckId: number, data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/client/${foodTruckId}`, { params: data }).then(success).catch(fail);
}

export async function editFoodTruck(foodTruckId: number, data: FormData, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  await api.patch(`/${foodTruckId}`, data, config).then(success).catch(fail);
}

export async function deleteFoodTruck(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.delete(`/${foodTruckId}`).then(success).catch(fail);
}

export async function foodTruckLike(data: Object, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.post(`/like`, data).then(success).catch(fail);
}

export async function editSelectedFoodTruck(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.patch(`/selected/${foodTruckId}`).then(success).catch(fail);
}

export async function isOpenFoodTruck(foodTruckId: number, success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/open/${foodTruckId}`).then(success).catch(fail);
}

export async function getLikedFoodTruck( success: any, fail: any) {
  api.defaults.headers["Authorization"] = getAuthorization();
  await api.get(`/like`).then(success).catch(fail);
}
