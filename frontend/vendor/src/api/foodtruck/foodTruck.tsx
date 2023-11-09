import { foodTruckApi, foodTruckFormApi } from "../index";

const api = foodTruckApi
const formApi = foodTruckFormApi

export async function createFoodTruck(data: FormData, success: any, fail: any) {
    await formApi.post(`/vendor`, data).then(success).catch(fail);
}

export async function getFoodTruckMarkers(data: Object, success: any, fail: any) {
    await api.get(`/marker`, { params: data }).then(success).catch(fail);
}

export async function getSearchedFoodTrucks(data: Object, success: any, fail: any) {
    await api.get(``, { params: data }).then(success).catch(fail);
}

export async function getFoodTruckOverviews(data: Object, success: any, fail: any) {
    await api.get(`/overview`, { params: data }).then(success).catch(fail);
}

export async function getFoodTruck(foodTruckId: number, data: Object, success: any, fail: any) {
    await api.get(`/${foodTruckId}`, { params: data }).then(success).catch(fail);
}

export async function editFoodTruck(foodTruckId: number, data: Object, success: any, fail: any) {
    await api.patch(`/${foodTruckId}`, data).then(success).catch(fail);
}

export async function deleteFoodTruck(foodTruckId: number, success: any, fail: any) {
    await api.delete(`/${foodTruckId}`).then(success).catch(fail);
}

export async function foodTruckLike(data: Object, success: any, fail: any) {
    await api.post(`/like`, data).then(success).catch(fail);
}

