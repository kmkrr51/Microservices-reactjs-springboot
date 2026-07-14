package com.snowrepo.itsm.application;

import com.snowrepo.itsm.domain.Incident;
import com.snowrepo.itsm.domain.Incident.IncidentStatus;
import com.snowrepo.itsm.domain.Incident.Priority;
import com.snowrepo.itsm.domain.IncidentRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class IncidentService {

  private final IncidentRepository incidentRepository;

  public Incident createIncident(
      String incidentNumber,
      String title,
      String description,
      Priority priority,
      String reporter,
      String createdBy
  ) {
    log.info("Creating incident: {}", incidentNumber);

    Incident incident = Incident.create(
        incidentNumber,
        title,
        description,
        priority,
        reporter,
        createdBy
    );

    return incidentRepository.save(incident);
  }

  public Incident getIncident(UUID id) {
    log.info("Fetching incident: {}", id);
    return incidentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Incident not found: " + id));
  }

  public Incident getIncidentByNumber(String incidentNumber) {
    log.info("Fetching incident by number: {}", incidentNumber);
    return incidentRepository.findByIncidentNumber(incidentNumber)
        .orElseThrow(() -> new RuntimeException("Incident not found: " + incidentNumber));
  }

  public List<Incident> getAllIncidents() {
    log.info("Fetching all incidents");
    return incidentRepository.findAll();
  }

  public Incident updateIncidentStatus(UUID id, IncidentStatus newStatus, String updatedBy) {
    log.info("Updating incident status: {} -> {}", id, newStatus);

    Incident incident = getIncident(id);
    incident.updateStatus(newStatus, updatedBy);

    return incidentRepository.save(incident);
  }

  public Incident assignIncident(UUID id, String assignee, String updatedBy) {
    log.info("Assigning incident {} to {}", id, assignee);

    Incident incident = getIncident(id);
    incident.assignTo(assignee, updatedBy);

    return incidentRepository.save(incident);
  }

  public void deleteIncident(UUID id) {
    log.info("Deleting incident: {}", id);
    incidentRepository.deleteById(id);
  }
}
