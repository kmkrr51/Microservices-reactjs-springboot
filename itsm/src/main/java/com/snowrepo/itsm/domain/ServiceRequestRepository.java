package com.snowrepo.itsm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {
  Optional<ServiceRequest> findByRequestNumber(String requestNumber);
}
