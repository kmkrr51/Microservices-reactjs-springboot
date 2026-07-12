import { createServiceClient, SERVICE_URLS } from "./api";

export interface ConfigurationItem {
  id: string;
  name: string;
  type: string;
  description?: string;
  status: "ACTIVE" | "INACTIVE" | "RETIRED" | "PENDING" | "ARCHIVED";
  owner?: string;
  location?: string;
  costCenter?: string;
  businessService?: string;
  createdAt: string;
  updatedAt: string;
}

const cmdbClient = createServiceClient(SERVICE_URLS.CMDB);

export const cmdbService = {
  getCIs: async (): Promise<ConfigurationItem[]> => {
    const response = await cmdbClient.get<ConfigurationItem[]>("/api/v1/cis");
    return response.data;
  },

  getCIById: async (id: string): Promise<ConfigurationItem> => {
    const response = await cmdbClient.get<ConfigurationItem>(`/api/v1/cis/${id}`);
    return response.data;
  },

  getCIByName: async (name: string): Promise<ConfigurationItem> => {
    const response = await cmdbClient.get<ConfigurationItem>(
      `/api/v1/cis/name/${name}`,
    );
    return response.data;
  },

  createCI: async (data: {
    name: string;
    type: string;
    description?: string;
    owner?: string;
  }): Promise<ConfigurationItem> => {
    const response = await cmdbClient.post<ConfigurationItem>("/api/v1/cis", data);
    return response.data;
  },

  updateCI: async (
    id: string,
    data: Partial<ConfigurationItem>,
  ): Promise<ConfigurationItem> => {
    const response = await cmdbClient.patch<ConfigurationItem>(
      `/api/v1/cis/${id}`,
      data,
    );
    return response.data;
  },

  changeCIStatus: async (
    id: string,
    status: ConfigurationItem["status"],
  ): Promise<ConfigurationItem> => {
    const response = await cmdbClient.put<ConfigurationItem>(
      `/api/v1/cis/${id}/status?status=${status}`,
    );
    return response.data;
  },

  deleteCI: async (id: string): Promise<void> => {
    await cmdbClient.delete(`/api/v1/cis/${id}`);
  },
};
