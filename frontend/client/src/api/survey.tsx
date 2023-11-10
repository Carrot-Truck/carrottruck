import { surveyApi, authorization } from "./index";

const api = surveyApi

export async function submitSurvey(data: Object, success: any, fail: any) {
    api.defaults.headers["Authorization"] = authorization
    await api.post(`/submit`, data).then(success).catch(fail);
}

export async function getSurveyCount(data: Object, success: any, fail: any) {
    await api.get(`/list`, { params: data }).then(success).catch(fail);
}

export async function getSurveyDetails(categoryId: number, data: Object, success: any, fail: any) {
    await api.get(`/list/${categoryId}`, { params: data }).then(success).catch(fail);
}

export async function deleteSurvey(surveyId: number, success: any, fail: any) {
    api.defaults.headers["Authorization"] = authorization
    await api.post(`/remove/${surveyId}`).then(success).catch(fail);
}
