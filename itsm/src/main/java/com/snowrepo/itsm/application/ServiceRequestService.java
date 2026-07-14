package com.snowrepo.itsm.application;

import com.snowrepo.itsm.domain.ServiceRequest;
import com.snowrepo.itsm.domain.ServiceRequest.RequestPriority;
import com.snowrepo.itsm.domain.ServiceRequest.RequestStatus;
import com.snowrepo.itsm.domain.ServiceRequest.RequestType;
import com.snowrepo.itsm.domain.ServiceRequestRepository;
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
public class ServiceRequestService {

  private final ServiceRequestRepository serviceRequestRepository;

  public ServiceRequest createRequest(String requestNumber, String title, String description,
      RequestType requestType, RequestPriority priority, String requester,
      String requestedService, String createdBy) {
    log.info("Creating service request: {}", requestNumber);
    ServiceRequest request = ServiceRequest.create(requestNumber, title, description,
        requestType, priority, requester, requestedService, createdBy);
    return serviceRequestRepository.save(request);
  }

  public ServiceRequest getRequest(UUID id) {
    return serviceRequestRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Service request not found: " + id));
  }

  public List<ServiceRequest> getAllRequests() {
    return serviceRequestRepository.findAll();
  }

  public ServiceRequest updateStatus(UUID id, RequestStatus status, String updatedBy) {
    ServiceRequest request = getRequest(id);
    request.updateStatus(status, updatedBy);
    return serviceRequestRepository.save(request);
  }

  public ServiceRequest updateRequest(UUID id, String title, String description,
      String assignedTo, String fulfillmentDetails, String updatedBy) {
    ServiceRequest request = getRequest(id);
    if (title != null) request.setTitle(title);
    if (description != null) request.setDescription(description);
    if (assignedTo != null) request.setAssignedTo(assignedTo);
    if (fulfillmentDetails != null) request.setFulfillmentDetails(fulfillmentDetails);
    request.setUpdatedBy(updatedBy);
    request.setUpdatedAt(java.time.LocalDateTime.now());
    return serviceRequestRepository.save(request);
  }

  public void deleteRequest(UUID id) {
    serviceRequestRepository.deleteById(id);
  }
}
