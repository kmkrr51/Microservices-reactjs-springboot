import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "@/services/api";
import { Incident } from "@/types";

export const fetchIncidents = createAsyncThunk(
  "incidents/fetchIncidents",
  async (_, { rejectWithValue }) => {
    try {
      const response = await apiClient.get<Incident[]>(
        "/api/v1/incidents",
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to fetch incidents",
      );
    }
  },
);

export const fetchIncidentById = createAsyncThunk(
  "incidents/fetchIncidentById",
  async (id: string, { rejectWithValue }) => {
    try {
      const response = await apiClient.get<Incident>(
        `/api/v1/incidents/${id}`,
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to fetch incident",
      );
    }
  },
);

export const createIncident = createAsyncThunk(
  "incidents/createIncident",
  async (data: any, { rejectWithValue }) => {
    try {
      const createResponse = await apiClient.post<Incident>(
        "/api/v1/incidents",
        data,
      );
      return createResponse.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to create incident",
      );
    }
  },
);

export const updateIncidentThunk = createAsyncThunk(
  "incidents/updateIncident",
  async (
    { id, data }: { id: string; data: any },
    { rejectWithValue },
  ) => {
    try {
      const response = await apiClient.put<Incident>(
        `/api/v1/incidents/${id}/status?status=${data.status}`,
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to update incident",
      );
    }
  },
);
