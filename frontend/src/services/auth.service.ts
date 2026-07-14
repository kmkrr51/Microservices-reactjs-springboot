import axios from "axios";
import { User } from "@/types";

const authClient = axios.create({
  baseURL: "http://localhost:8089",
  headers: {
    "Content-Type": "application/json",
  },
});

authClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

export const authService = {
  login: async (credentials: {
    email: string;
    password: string;
  }): Promise<{ token: string; user: User }> => {
    try {
      const response = await authClient.post<{ token: string; user: User }>(
        "/api/v1/auth/login",
        credentials,
      );
      const { token, user } = response.data;
      localStorage.setItem("token", token);
      return { token, user };
    } catch (error: any) {
      throw new Error(error.response?.data?.message || "Login failed");
    }
  },

  logout: (): void => {
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    window.location.href = "/login";
  },

  getCurrentUser: async (): Promise<User> => {
    try {
      const response = await authClient.get<User>("/api/v1/auth/me");
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || "Failed to get user");
    }
  },

  isAuthenticated: (): boolean => {
    return !!localStorage.getItem("token");
  },

  getToken: (): string | null => {
    return localStorage.getItem("token");
  },
};
