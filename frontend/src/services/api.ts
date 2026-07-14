import axios, { AxiosInstance, AxiosError } from "axios";
import { ApiError, ApiErrorResponse } from "@/types";

// Service URLs for Spring Boot microservices
export const SERVICE_URLS = {
  CMDB: "http://localhost:8015",
  ITSM: "http://localhost:8016",
  ITOM: "http://localhost:8018",
  ITAM: "http://localhost:8017",
  API_GATEWAY: "http://localhost:8089",
};

const API_BASE_URL = SERVICE_URLS.ITSM;

const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError<ApiErrorResponse>) => {
    const apiError: ApiError = new Error(
      error.response?.data?.message || error.message,
    ) as ApiError;
    apiError.status = error.response?.status || 500;
    apiError.data = error.response?.data;

    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("refreshToken");
      window.location.href = "/login";
    }

    return Promise.reject(apiError);
  },
);

export const createServiceClient = (baseURL: string): AxiosInstance => {
  const client = axios.create({
    baseURL,
    headers: {
      "Content-Type": "application/json",
    },
  });

  client.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("token");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => Promise.reject(error),
  );

  client.interceptors.response.use(
    (response) => response,
    (error: AxiosError<ApiErrorResponse>) => {
      const apiError: ApiError = new Error(
        error.response?.data?.message || error.message,
      ) as ApiError;
      apiError.status = error.response?.status || 500;
      apiError.data = error.response?.data;

      if (error.response?.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "/login";
      }

      return Promise.reject(apiError);
    },
  );

  return client;
};

export default apiClient;

