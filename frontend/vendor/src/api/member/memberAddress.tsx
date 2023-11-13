import { memberAddressApi } from "../index";

const api = memberAddressApi;

export async function createMemberAddress(data: Object, success: any, fail: any) {
  await api.post(``, data).then(success).catch(fail);
}

export async function getMemberAddresses(data: Object, success: any, fail: any) {
  await api.get(``, { params: data }).then(success).catch(fail);
}

export async function getMemberAddress(memberAddressId: number, success: any, fail: any) {
  await api.get(`/${memberAddressId}}`).then(success).catch(fail);
}

export async function editMemberAddress(
  memberAddressId: number,
  data: Object,
  success: any,
  fail: any
) {
  await api.patch(`/${memberAddressId}}`, data).then(success).catch(fail);
}

export async function editSelectedMemberAddress(data: Object, success: any, fail: any) {
  await api.patch(`/selected`, data).then(success).catch(fail);
}

export async function deleteMemberAddress(memberAddressId: number, success: any, fail: any) {
  await api.delete(`/${memberAddressId}`).then(success).catch(fail);
}
