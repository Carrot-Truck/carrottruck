import axios from "axios";

export const getAuthorization = (): string => {
  return `${localStorage["grantType"]} ${localStorage["accessToken"]}`;
};

export const analysisApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/analysis`,
  headers: {
    "Content-type": "application/json",
  },
});

export const authApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/auth`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const cartApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/cart`,
  headers: {
    "Content-type": "application/json",
  },
});

export const clientApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/member/client`,
  headers: {
    "Content-type": "application/json",
  },
});

export const categoryApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/category`,
  headers: {
    "Content-type": "application/json",
  },
});

export const foodTruckApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/food-truck`,
  headers: {
    "Content-type": "application/json",
  },
});

export const foodTruckFormApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/food-truck`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const memberAddressApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/member/adderss`,
  headers: {
    "Content-type": "application/json",
  },
});

export const menuApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/menu`,
  headers: {
    "Content-type": "application/json",
  },
});

export const menuFormApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/menu`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const orderApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/order`,
  headers: {
    "Content-type": "application/json",
  },
});

export const paymentApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/verifyIamport`,
  headers: {
    "Content-type": "application/json",
  },
});

export const reviewApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/review`,
  headers: {
    "Content-type": "application/json",
  },
});

export const reviewFormApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/review`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const saleApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/sale`,
  headers: {
    "Content-type": "application/json",
  },
});

export const scheduleApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/schedule`,
  headers: {
    "Content-type": "application/json",
  },
});

export const statisticsApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/statistics`,
  headers: {
    "Content-type": "application/json",
  },
});

export const surveyApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/survey`,
  headers: {
    "Content-type": "application/json",
  },
});

export const vendorApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/member/vendor`,
  headers: {
    "Content-type": "application/json",
  },
});

export const vendorInfoApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/member/vendor-info`,
  headers: {
    "Content-type": "application/json",
  },
});

export const addressApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/address`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});
