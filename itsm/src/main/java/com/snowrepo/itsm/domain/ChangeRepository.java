package com.snowrepo.itsm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChangeRepository extends JpaRepository<Change, UUID> {
  Optional<Change> findByChangeNumber(String changeNumber);
}
