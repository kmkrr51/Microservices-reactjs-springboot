package com.snowrepo.itsm.application;

import com.snowrepo.itsm.domain.Change;
import com.snowrepo.itsm.domain.Change.ChangeStatus;
import com.snowrepo.itsm.domain.Change.ChangeType;
import com.snowrepo.itsm.domain.Change.RiskLevel;
import com.snowrepo.itsm.domain.ChangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChangeService {

  private final ChangeRepository changeRepository;

  public Change createChange(String changeNumber, String title, String description,
      ChangeType type, RiskLevel riskLevel, String createdBy) {
    log.info("Creating change: {}", changeNumber);
    Change change = Change.create(changeNumber, title, description, type, riskLevel, createdBy);
    return changeRepository.save(change);
  }

  public Change getChange(UUID id) {
    return changeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Change not found: " + id));
  }

  public List<Change> getAllChanges() {
    return changeRepository.findAll();
  }

  public Change updateStatus(UUID id, ChangeStatus status, String updatedBy) {
    Change change = getChange(id);
    change.updateStatus(status, updatedBy);
    return changeRepository.save(change);
  }

  public Change updateChange(UUID id, String title, String description, String impactAssessment,
      String rollbackPlan, String implementationSchedule, String updatedBy) {
    Change change = getChange(id);
    if (title != null) change.setTitle(title);
    if (description != null) change.setDescription(description);
    if (impactAssessment != null) change.setImpactAssessment(impactAssessment);
    if (rollbackPlan != null) change.setRollbackPlan(rollbackPlan);
    if (implementationSchedule != null) change.setImplementationSchedule(implementationSchedule);
    change.setUpdatedBy(updatedBy);
    change.setUpdatedAt(java.time.LocalDateTime.now());
    return changeRepository.save(change);
  }

  public void deleteChange(UUID id) {
    changeRepository.deleteById(id);
  }
}
