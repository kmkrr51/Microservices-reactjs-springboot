import apiClient from "./api";
import { Incident } from "@/types";

export const incidentService = {
  getIncidents: async (): Promise<Incident[]> => {
    const response = await apiClient.get<Incident[]>("/api/v1/incidents");
    return response.data;
  },

  getIncidentById: async (id: string): Promise<Incident> => {
    const response = await apiClient.get<Incident>(`/api/v1/incidents/${id}`);
    return response.data;
  },

  getIncidentByNumber: async (incidentNumber: string): Promise<Incident> => {
    const response = await apiClient.get<Incident>(
      `/api/v1/incidents/number/${incidentNumber}`,
    );
    return response.data;
  },

  createIncident: async (data: {
    incidentNumber: string;
    title: string;
    description: string;
    priority: "CRITICAL" | "HIGH" | "MEDIUM" | "LOW";
    reporter: string;
  }): Promise<Incident> => {
    const response = await apiClient.post<Incident>("/api/v1/incidents", data);
    return response.data;
  },

  updateIncidentStatus: async (
    id: string,
    status: "NEW" | "ASSIGNED" | "IN_PROGRESS" | "PENDING" | "RESOLVED" | "CLOSED" | "REOPENED",
  ): Promise<Incident> => {
    const response = await apiClient.put<Incident>(
      `/api/v1/incidents/${id}/status?status=${status}`,
    );
    return response.data;
  },

  assignIncident: async (id: string, assignee: string): Promise<Incident> => {
    const response = await apiClient.put<Incident>(
      `/api/v1/incidents/${id}/assign?assignee=${assignee}`,
    );
    return response.data;
  },

  deleteIncident: async (id: string): Promise<void> => {
    await apiClient.delete(`/api/v1/incidents/${id}`);
  },
};
