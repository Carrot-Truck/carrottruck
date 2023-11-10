import { menuApi, menuFormApi } from "./index";

const api = menuApi
const formApi = menuFormApi

export async function createMenu(data: FormData, success: any, fail: any) {
    await formApi.post(``, data).then(success).catch(fail);
}

export async function getMenus(data: number, success: any, fail: any) {
    await api.get(``, { params: { foodTruckId: data } }).then(success).catch(fail);
}


export async function getMenu(menuId: number, data: Object, success: any, fail: any) {
    await api.get(`/${menuId}`, { params: data }).then(success).catch(fail);
}

export async function editMenu(menuId: number, data: FormData, success: any, fail: any) {
    await formApi.patch(`/${menuId}`, data).then(success).catch(fail);
}

export async function deleteMenu(menuId: number, success: any, fail: any) {
    await api.delete(`/${menuId}`).then(success).catch(fail);
}

export async function createMenuOption(menuId: number, data: Object, success: any, fail: any) {
    await api.post(`/${menuId}/option`, data).then(success).catch(fail);
}

export async function deleteMenuOption(menuOptionId: number, success: any, fail: any) {
    await api.delete(`/option/${menuOptionId}`).then(success).catch(fail);
}
