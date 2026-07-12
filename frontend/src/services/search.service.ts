import { createServiceClient, SERVICE_URLS } from "./api";

export interface SearchResult {
  id: string;
  type: "incident" | "cmdb" | "asset" | "hardware";
  title: string;
  description?: string;
  status?: string;
  createdAt: string;
}

export interface SearchFilters {
  query?: string;
  type?: string;
  status?: string;
  dateFrom?: string;
  dateTo?: string;
  sortBy?: "relevance" | "date" | "status";
  limit?: number;
}

const itsmClient = createServiceClient(SERVICE_URLS.ITSM);
const cmdbClient = createServiceClient(SERVICE_URLS.CMDB);
const itomClient = createServiceClient(SERVICE_URLS.ITOM);
const itamClient = createServiceClient(SERVICE_URLS.ITAM);

export const searchService = {
  searchIncidents: async (filters: SearchFilters): Promise<SearchResult[]> => {
    try {
      const params = new URLSearchParams();
      if (filters.query) params.append("query", filters.query);
      if (filters.status) params.append("status", filters.status);

      const response = await itsmClient.get<any[]>(
        `/api/v1/incidents?${params.toString()}`,
      );

      return response.data.map((incident) => ({
        id: incident.id,
        type: "incident",
        title: incident.title,
        description: incident.description,
        status: incident.status,
        createdAt: incident.createdAt,
      }));
    } catch (error) {
      console.error("Search incidents failed:", error);
      return [];
    }
  },

  searchCIs: async (filters: SearchFilters): Promise<SearchResult[]> => {
    try {
      const params = new URLSearchParams();
      if (filters.query) params.append("query", filters.query);
      if (filters.status) params.append("status", filters.status);

      const response = await cmdbClient.get<any[]>(
        `/api/v1/cis?${params.toString()}`,
      );

      return response.data.map((ci) => ({
        id: ci.id,
        type: "cmdb",
        title: ci.name,
        description: ci.description,
        status: ci.status,
        createdAt: ci.createdAt,
      }));
    } catch (error) {
      console.error("Search CIs failed:", error);
      return [];
    }
  },

  searchAssets: async (filters: SearchFilters): Promise<SearchResult[]> => {
    try {
      const params = new URLSearchParams();
      if (filters.query) params.append("query", filters.query);
      if (filters.status) params.append("status", filters.status);

      const response = await itomClient.get<any[]>(
        `/api/v1/assets?${params.toString()}`,
      );

      return response.data.map((asset) => ({
        id: asset.id,
        type: "asset",
        title: asset.name,
        description: asset.description,
        status: asset.status,
        createdAt: asset.createdAt,
      }));
    } catch (error) {
      console.error("Search assets failed:", error);
      return [];
    }
  },

  searchHardware: async (filters: SearchFilters): Promise<SearchResult[]> => {
    try {
      const params = new URLSearchParams();
      if (filters.query) params.append("query", filters.query);
      if (filters.status) params.append("status", filters.status);

      const response = await itamClient.get<any[]>(
        `/api/v1/assets?${params.toString()}`,
      );

      return response.data.map((asset) => ({
        id: asset.id,
        type: "hardware",
        title: asset.name,
        description: asset.description,
        status: asset.status,
        createdAt: asset.createdAt,
      }));
    } catch (error) {
      console.error("Search hardware failed:", error);
      return [];
    }
  },

  globalSearch: async (filters: SearchFilters): Promise<SearchResult[]> => {
    try {
      const [incidents, cis, assets, hardware] = await Promise.all([
        searchService.searchIncidents(filters),
        searchService.searchCIs(filters),
        searchService.searchAssets(filters),
        searchService.searchHardware(filters),
      ]);

      const results = [...incidents, ...cis, ...assets, ...hardware];

      if (filters.sortBy === "date") {
        results.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime(),
        );
      }

      return filters.limit ? results.slice(0, filters.limit) : results;
    } catch (error) {
      console.error("Global search failed:", error);
      return [];
    }
  },
};
