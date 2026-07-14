import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "@/services/api";
import { Change } from "@/types";

export const fetchChanges = createAsyncThunk(
  "changes/fetchChanges",
  async (_, { rejectWithValue }) => {
    try {
      const response = await apiClient.get<Change[]>(
        "/api/v1/changes",
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to fetch changes",
      );
    }
  },
);

export const fetchChangeById = createAsyncThunk(
  "changes/fetchChangeById",
  async (id: string, { rejectWithValue }) => {
    try {
      const response = await apiClient.get<Change>(
        `/api/v1/changes/${id}`,
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to fetch change",
      );
    }
  },
);

export const createChange = createAsyncThunk(
  "changes/createChange",
  async (data: any, { rejectWithValue }) => {
    try {
      const response = await apiClient.post<Change>(
        "/api/v1/changes",
        data,
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to create change",
      );
    }
  },
);

export const updateChangeThunk = createAsyncThunk(
  "changes/updateChange",
  async (
    { id, data }: { id: string; data: any },
    { rejectWithValue },
  ) => {
    try {
      const response = await apiClient.patch<Change>(
        `/api/v1/changes/${id}`,
        data,
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.message || "Failed to update change",
      );
    }
  },
);
