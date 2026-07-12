import { createServiceClient, SERVICE_URLS } from "./api";

export interface DashboardMetrics {
  totalIncidents: number;
  openIncidents: number;
  resolvedIncidents: number;
  totalCIs: number;
  activeCIs: number;
  totalAssets: number;
  monitoredAssets: number;
  totalHardware: number;
  activeHardware: number;
}

export interface IncidentMetrics {
  byStatus: Record<string, number>;
  byPriority: Record<string, number>;
  avgResolutionTime: number;
  totalResolved: number;
}

export interface AssetMetrics {
  byType: Record<string, number>;
  byStatus: Record<string, number>;
  discoveryRate: number;
  monitoringCoverage: number;
}

const itsmClient = createServiceClient(SERVICE_URLS.ITSM);
const cmdbClient = createServiceClient(SERVICE_URLS.CMDB);
const itomClient = createServiceClient(SERVICE_URLS.ITOM);
const itamClient = createServiceClient(SERVICE_URLS.ITAM);

export const analyticsService = {
  getDashboardMetrics: async (): Promise<DashboardMetrics> => {
    try {
      const [incidents, cis, itomAssets, itamAssets] = await Promise.all([
        itsmClient.get<any[]>("/api/v1/incidents"),
        cmdbClient.get<any[]>("/api/v1/cis"),
        itomClient.get<any[]>("/api/v1/assets"),
        itamClient.get<any[]>("/api/v1/assets"),
      ]);

      const incidentData = incidents.data || [];
      const ciData = cis.data || [];
      const itomData = itomAssets.data || [];
      const itamData = itamAssets.data || [];

      return {
        totalIncidents: incidentData.length,
        openIncidents: incidentData.filter(
          (i) => i.status !== "RESOLVED" && i.status !== "CLOSED",
        ).length,
        resolvedIncidents: incidentData.filter(
          (i) => i.status === "RESOLVED" || i.status === "CLOSED",
        ).length,
        totalCIs: ciData.length,
        activeCIs: ciData.filter((c) => c.status === "ACTIVE").length,
        totalAssets: itomData.length,
        monitoredAssets: itomData.filter((a) => a.status === "MONITORED").length,
        totalHardware: itamData.length,
        activeHardware: itamData.filter((h) => h.status === "ACTIVE").length,
      };
    } catch (error) {
      console.error("Failed to get dashboard metrics:", error);
      return {
        totalIncidents: 0,
        openIncidents: 0,
        resolvedIncidents: 0,
        totalCIs: 0,
        activeCIs: 0,
        totalAssets: 0,
        monitoredAssets: 0,
        totalHardware: 0,
        activeHardware: 0,
      };
    }
  },

  getIncidentMetrics: async (): Promise<IncidentMetrics> => {
    try {
      const response = await itsmClient.get<any[]>("/api/v1/incidents");
      const incidents = response.data || [];

      const byStatus: Record<string, number> = {};
      const byPriority: Record<string, number> = {};

      incidents.forEach((incident) => {
        byStatus[incident.status] = (byStatus[incident.status] || 0) + 1;
        byPriority[incident.priority] = (byPriority[incident.priority] || 0) + 1;
      });

      const resolved = incidents.filter(
        (i) => i.status === "RESOLVED" || i.status === "CLOSED",
      );

      return {
        byStatus,
        byPriority,
        avgResolutionTime: resolved.length > 0 ? 24 : 0,
        totalResolved: resolved.length,
      };
    } catch (error) {
      console.error("Failed to get incident metrics:", error);
      return {
        byStatus: {},
        byPriority: {},
        avgResolutionTime: 0,
        totalResolved: 0,
      };
    }
  },

  getAssetMetrics: async (): Promise<AssetMetrics> => {
    try {
      const response = await itomClient.get<any[]>("/api/v1/assets");
      const assets = response.data || [];

      const byType: Record<string, number> = {};
      const byStatus: Record<string, number> = {};

      assets.forEach((asset) => {
        byType[asset.assetType] = (byType[asset.assetType] || 0) + 1;
        byStatus[asset.status] = (byStatus[asset.status] || 0) + 1;
      });

      const monitored = assets.filter((a) => a.status === "MONITORED").length;

      return {
        byType,
        byStatus,
        discoveryRate: assets.length > 0 ? 100 : 0,
        monitoringCoverage: assets.length > 0 ? (monitored / assets.length) * 100 : 0,
      };
    } catch (error) {
      console.error("Failed to get asset metrics:", error);
      return {
        byType: {},
        byStatus: {},
        discoveryRate: 0,
        monitoringCoverage: 0,
      };
    }
  },

  getRecentIncidents: async (limit: number = 5): Promise<any[]> => {
    try {
      const response = await itsmClient.get<any[]>("/api/v1/incidents");
      const incidents = response.data || [];
      return incidents.slice(0, limit);
    } catch (error) {
      console.error("Failed to get recent incidents:", error);
      return [];
    }
  },

  getRecentCIs: async (limit: number = 5): Promise<any[]> => {
    try {
      const response = await cmdbClient.get<any[]>("/api/v1/cis");
      const cis = response.data || [];
      return cis.slice(0, limit);
    } catch (error) {
      console.error("Failed to get recent CIs:", error);
      return [];
    }
  },
};
