package com.snowrepo.itsm.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.snowrepo.itsm.domain.Incident.IncidentStatus;
import com.snowrepo.itsm.domain.Incident.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidentTest {

  private Incident incident;

  @BeforeEach
  void setUp() {
    incident = Incident.create(
        "INC-001",
        "System Down",
        "Critical system outage",
        Priority.CRITICAL,
        "user@example.com",
        "admin"
    );
  }

  @Test
  void testCreateIncident() {
    assertNotNull(incident.getId());
    assertEquals("INC-001", incident.getIncidentNumber());
    assertEquals("System Down", incident.getTitle());
    assertEquals("Critical system outage", incident.getDescription());
    assertEquals(Priority.CRITICAL, incident.getPriority());
    assertEquals(IncidentStatus.NEW, incident.getStatus());
    assertEquals("user@example.com", incident.getReporter());
    assertNotNull(incident.getCreatedAt());
    assertNull(incident.getResolvedAt());
  }

  @Test
  void testUpdateStatus() {
    incident.updateStatus(IncidentStatus.IN_PROGRESS, "admin");

    assertEquals(IncidentStatus.IN_PROGRESS, incident.getStatus());
    assertNotNull(incident.getUpdatedAt());
    assertEquals(2L, incident.getVersion());
  }

  @Test
  void testResolveIncident() {
    incident.updateStatus(IncidentStatus.RESOLVED, "admin");

    assertEquals(IncidentStatus.RESOLVED, incident.getStatus());
    assertNotNull(incident.getResolvedAt());
  }

  @Test
  void testAssignIncident() {
    incident.assignTo("support@example.com", "admin");

    assertEquals("support@example.com", incident.getAssignedTo());
    assertEquals(2L, incident.getVersion());
  }

  @Test
  void testIncidentStatusTransitions() {
    incident.updateStatus(IncidentStatus.ASSIGNED, "admin");
    assertEquals(IncidentStatus.ASSIGNED, incident.getStatus());

    incident.updateStatus(IncidentStatus.IN_PROGRESS, "admin");
    assertEquals(IncidentStatus.IN_PROGRESS, incident.getStatus());

    incident.updateStatus(IncidentStatus.RESOLVED, "admin");
    assertEquals(IncidentStatus.RESOLVED, incident.getStatus());

    incident.updateStatus(IncidentStatus.CLOSED, "admin");
    assertEquals(IncidentStatus.CLOSED, incident.getStatus());
  }

  @Test
  void testNoStatusChangeWhenSame() {
    Long initialVersion = incident.getVersion();
    incident.updateStatus(IncidentStatus.NEW, "admin");

    assertEquals(initialVersion, incident.getVersion());
  }

  @Test
  void testIncidentDomainEvents() {
    assertNotNull(incident.getDomainEvents());
    assertEquals(1, incident.getDomainEvents().size());

    incident.updateStatus(IncidentStatus.IN_PROGRESS, "admin");
    assertEquals(2, incident.getDomainEvents().size());

    incident.assignTo("support@example.com", "admin");
    assertEquals(3, incident.getDomainEvents().size());
  }
}
