export interface Notification {
  id: string;
  type: "incident" | "cmdb" | "asset" | "system";
  title: string;
  message: string;
  severity: "info" | "warning" | "error" | "success";
  timestamp: string;
  read: boolean;
  actionUrl?: string;
}

class NotificationManager {
  private notifications: Notification[] = [];
  private listeners: Set<(notifications: Notification[]) => void> = new Set();

  subscribe(callback: (notifications: Notification[]) => void): () => void {
    this.listeners.add(callback);
    return () => this.listeners.delete(callback);
  }

  private notify() {
    this.listeners.forEach((callback) => callback([...this.notifications]));
  }

  addNotification(notification: Omit<Notification, "id" | "timestamp">): void {
    const newNotification: Notification = {
      ...notification,
      id: `notif-${Date.now()}`,
      timestamp: new Date().toISOString(),
    };
    this.notifications.unshift(newNotification);
    this.notify();
  }

  markAsRead(id: string): void {
    const notification = this.notifications.find((n) => n.id === id);
    if (notification) {
      notification.read = true;
      this.notify();
    }
  }

  markAllAsRead(): void {
    this.notifications.forEach((n) => (n.read = true));
    this.notify();
  }

  removeNotification(id: string): void {
    this.notifications = this.notifications.filter((n) => n.id !== id);
    this.notify();
  }

  clearAll(): void {
    this.notifications = [];
    this.notify();
  }

  getUnreadCount(): number {
    return this.notifications.filter((n) => !n.read).length;
  }

  getNotifications(): Notification[] {
    return [...this.notifications];
  }

  simulateIncidentNotification(incidentId: string, title: string): void {
    this.addNotification({
      type: "incident",
      title: "New Incident",
      message: `Incident "${title}" has been created`,
      severity: "warning",
      read: false,
      actionUrl: `/incidents/${incidentId}`,
    });
  }

  simulateCMDBNotification(ciId: string, action: string): void {
    this.addNotification({
      type: "cmdb",
      title: "CMDB Update",
      message: `Configuration item has been ${action}`,
      severity: "info",
      read: false,
      actionUrl: `/cmdb/${ciId}`,
    });
  }

  simulateAssetNotification(assetId: string, action: string): void {
    this.addNotification({
      type: "asset",
      title: "Asset Update",
      message: `Asset has been ${action}`,
      severity: "info",
      read: false,
      actionUrl: `/itom/${assetId}`,
    });
  }

  simulateSystemNotification(message: string, severity: "info" | "warning" | "error" = "info"): void {
    this.addNotification({
      type: "system",
      title: "System Notification",
      message,
      severity,
      read: false,
    });
  }
}

export const notificationManager = new NotificationManager();
