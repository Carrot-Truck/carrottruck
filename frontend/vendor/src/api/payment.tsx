import { paymentApi } from './index';

const api = paymentApi;

export async function paymentByImpUid(impUid: string, success: any, fail: any) {
  await api.post(`/${impUid}`).then(success).catch(fail);
}
