import { createServiceClient, SERVICE_URLS } from "./api";

export interface DiscoveredAsset {
  id: string;
  name: string;
  assetType: string;
  description?: string;
  status: "DISCOVERED" | "MONITORED" | "DECOMMISSIONED" | "UNKNOWN";
  ipAddress?: string;
  hostname?: string;
  osType?: string;
  osVersion?: string;
  location?: string;
  owner?: string;
  lastDiscoveredAt: string;
  createdAt: string;
  updatedAt: string;
}

const itomClient = createServiceClient(SERVICE_URLS.ITOM);

export const itomService = {
  getAssets: async (): Promise<DiscoveredAsset[]> => {
    const response = await itomClient.get<DiscoveredAsset[]>("/api/v1/assets");
    return response.data;
  },

  getAssetById: async (id: string): Promise<DiscoveredAsset> => {
    const response = await itomClient.get<DiscoveredAsset>(`/api/v1/assets/${id}`);
    return response.data;
  },

  getAssetByHostname: async (hostname: string): Promise<DiscoveredAsset> => {
    const response = await itomClient.get<DiscoveredAsset>(
      `/api/v1/assets/hostname/${hostname}`,
    );
    return response.data;
  },

  getAssetByIpAddress: async (ipAddress: string): Promise<DiscoveredAsset> => {
    const response = await itomClient.get<DiscoveredAsset>(
      `/api/v1/assets/ip/${ipAddress}`,
    );
    return response.data;
  },

  createAsset: async (data: {
    name: string;
    assetType: string;
    description?: string;
    ipAddress?: string;
    hostname?: string;
  }): Promise<DiscoveredAsset> => {
    const response = await itomClient.post<DiscoveredAsset>(
      "/api/v1/assets",
      data,
    );
    return response.data;
  },

  updateAssetStatus: async (
    id: string,
    status: DiscoveredAsset["status"],
  ): Promise<DiscoveredAsset> => {
    const response = await itomClient.put<DiscoveredAsset>(
      `/api/v1/assets/${id}/status?status=${status}`,
    );
    return response.data;
  },

  recordDiscovery: async (id: string): Promise<DiscoveredAsset> => {
    const response = await itomClient.put<DiscoveredAsset>(
      `/api/v1/assets/${id}/discover`,
    );
    return response.data;
  },

  deleteAsset: async (id: string): Promise<void> => {
    await itomClient.delete(`/api/v1/assets/${id}`);
  },
};
