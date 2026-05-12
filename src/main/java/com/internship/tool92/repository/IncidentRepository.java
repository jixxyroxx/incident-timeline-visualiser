package com.internship.tool92.repository;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatus(IncidentStatus status);
    List<Incident> findByCreatedById(Long userId);
}