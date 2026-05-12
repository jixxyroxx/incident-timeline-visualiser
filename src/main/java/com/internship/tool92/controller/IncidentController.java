package com.internship.tool92.controller;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentStatus;
import com.internship.tool92.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    @PostMapping
    public ResponseEntity<Incident> createIncident(
            @RequestBody Incident incident) {
        return ResponseEntity.status(201)
            .body(incidentService.createIncident(incident));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(
            @PathVariable Long id,
            @RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.updateIncident(id, incident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.ok(Map.of("message",
            "Incident deleted successfully"));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Incident>> getByStatus(
            @PathVariable IncidentStatus status) {
        return ResponseEntity.ok(incidentService.getIncidentsByStatus(status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Incident>> getByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(incidentService.getIncidentsByUser(userId));
    }
}