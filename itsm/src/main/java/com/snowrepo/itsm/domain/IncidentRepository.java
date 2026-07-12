package com.snowrepo.itsm.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, UUID> {

  Optional<Incident> findByIncidentNumber(String incidentNumber);
}
