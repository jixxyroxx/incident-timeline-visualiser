package com.internship.tool92.service;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentStatus;
import com.internship.tool92.exception.ResourceNotFoundException;
import com.internship.tool92.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;

    @Cacheable(value = "incidents")
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @Cacheable(value = "incident", key = "#id")
    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Incident not found with id: " + id));
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident createIncident(Incident incident) {
        if (incident.getTitle() == null || incident.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (incident.getStatus() == null) {
            incident.setStatus(IncidentStatus.OPEN);
        }
        if (incident.getSeverity() == null) {
            throw new IllegalArgumentException("Severity cannot be empty");
        }
        return incidentRepository.save(incident);
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident updateIncident(Long id, Incident updated) {
        Incident existing = getIncidentById(id);

        if (updated.getTitle() != null && !updated.getTitle().isBlank()) {
            existing.setTitle(updated.getTitle());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getSeverity() != null) {
            existing.setSeverity(updated.getSeverity());
        }
        if (updated.getStatus() == IncidentStatus.RESOLVED
                && existing.getResolvedAt() == null) {
            existing.setResolvedAt(LocalDateTime.now());
        }

        return incidentRepository.save(existing);
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public void deleteIncident(Long id) {
        Incident existing = getIncidentById(id);
        incidentRepository.delete(existing);
    }

    @Cacheable(value = "incidentsByStatus", key = "#status")
    public List<Incident> getIncidentsByStatus(IncidentStatus status) {
        return incidentRepository.findByStatus(status);
    }

    @Cacheable(value = "incidentsByUser", key = "#userId")
    public List<Incident> getIncidentsByUser(Long userId) {
        return incidentRepository.findByCreatedById(userId);
    }
}