import { addressApi } from "./index";

const api = addressApi

export async function getSido(success: any, fail: any) {
    await api.get(`/sido`).then(success).catch(fail);
}

export async function getSigungu(sidoId: number, success: any, fail: any) {
    await api.get(`/sigungu/${sidoId}`).then(success).catch(fail);
}

export async function getDong(sigungu: number, success: any, fail: any) {
    await api.get(`/dong/${sigungu}`).then(success).catch(fail);
}
