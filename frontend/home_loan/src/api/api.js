import axios from "axios";


const BASE_URL = "http://localhost:8080";

const api = axios.create({ 
    baseURL: BASE_URL 
});


api.interceptors.request.use((config) => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (user && !config.url.includes("/auth/login") && !config.url.includes("/auth/register")) {
    const token = btoa(user.username + ":" + user.password);
    config.headers.Authorization = `Basic ${token}`;
  }

  return config;
});


export const registerUser = (data) => api.post("/auth/register", data);
export const loginUser = (data) => api.post("/auth/login", data);
export const getAccount = (userId) => api.get(`/account/user/${userId}`);
export const createAccount = (userId, data) => api.post(`/account/create/${userId}`, data);

export const applyLoan = (formData) =>api.post("/loan/apply", formData, {
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });

export const getLoan = (id) => api.get(`/loan/${id}`);
export const getLoanByAccount = (accountId) =>api.get(`/loan/account/${accountId}`);
export const getSchedule = (id) =>api.get(`/loan/schedule/${id}`);
export const getAllApplications = () =>api.get("/loan/applications");
export const approveLoan = (id) =>api.post(`/loan/approve/${id}`);
export const cancelLoan = (id) =>api.post(`/loan/cancel/${id}`);
export const payEmi = (id) => api.post(`/loan/pay-emi/${id}`);
export const forecloseLoan = (loanId) =>api.post(`/loan/foreclose/${loanId}`);
export const prepayLoan = (data) =>api.post(`/loan/prepay`, data);
export const exportCSV = (id) =>api.get(`/loan/export/${id}`, { responseType: "blob"
});