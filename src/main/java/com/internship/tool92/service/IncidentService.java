package com.internship.tool92.service;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentStatus;
import com.internship.tool92.exception.ResourceNotFoundException;
import com.internship.tool92.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Incident not found with id: " + id));
    }

    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public Incident updateIncident(Long id, Incident updated) {
        Incident existing = getIncidentById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setSeverity(updated.getSeverity());
        return incidentRepository.save(existing);
    }

    public void deleteIncident(Long id) {
        Incident existing = getIncidentById(id);
        incidentRepository.delete(existing);
    }

    public List<Incident> getIncidentsByStatus(IncidentStatus status) {
        return incidentRepository.findByStatus(status);
    }
}