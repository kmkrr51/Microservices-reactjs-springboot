import { createServiceClient, SERVICE_URLS } from "./api";

export interface HardwareAsset {
  id: string;
  assetTag: string;
  name: string;
  assetType: string;
  description?: string;
  status: "ACTIVE" | "INACTIVE" | "RETIRED" | "DISPOSED" | "LOST";
  manufacturer?: string;
  model?: string;
  serialNumber?: string;
  purchaseDate?: string;
  purchaseCost?: number;
  warrantyExpiryDate?: string;
  assignedTo?: string;
  location?: string;
  costCenter?: string;
  createdAt: string;
  updatedAt: string;
}

const itamClient = createServiceClient(SERVICE_URLS.ITAM);

export const itamService = {
  getAssets: async (): Promise<HardwareAsset[]> => {
    const response = await itamClient.get<HardwareAsset[]>("/api/v1/assets");
    return response.data;
  },

  getAssetById: async (id: string): Promise<HardwareAsset> => {
    const response = await itamClient.get<HardwareAsset>(`/api/v1/assets/${id}`);
    return response.data;
  },

  getAssetByTag: async (assetTag: string): Promise<HardwareAsset> => {
    const response = await itamClient.get<HardwareAsset>(
      `/api/v1/assets/tag/${assetTag}`,
    );
    return response.data;
  },

  getAssetBySerialNumber: async (serialNumber: string): Promise<HardwareAsset> => {
    const response = await itamClient.get<HardwareAsset>(
      `/api/v1/assets/serial/${serialNumber}`,
    );
    return response.data;
  },

  createAsset: async (data: {
    assetTag: string;
    name: string;
    assetType: string;
    manufacturer?: string;
    model?: string;
    serialNumber?: string;
    purchaseCost?: number;
  }): Promise<HardwareAsset> => {
    const response = await itamClient.post<HardwareAsset>(
      "/api/v1/assets",
      data,
    );
    return response.data;
  },

  updateAssetStatus: async (
    id: string,
    status: HardwareAsset["status"],
  ): Promise<HardwareAsset> => {
    const response = await itamClient.put<HardwareAsset>(
      `/api/v1/assets/${id}/status?status=${status}`,
    );
    return response.data;
  },

  assignAsset: async (id: string, assignee: string): Promise<HardwareAsset> => {
    const response = await itamClient.put<HardwareAsset>(
      `/api/v1/assets/${id}/assign?assignee=${assignee}`,
    );
    return response.data;
  },

  deleteAsset: async (id: string): Promise<void> => {
    await itamClient.delete(`/api/v1/assets/${id}`);
  },
};
