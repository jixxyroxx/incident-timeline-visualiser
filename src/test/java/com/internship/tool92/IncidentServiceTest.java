package com.internship.tool92;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentSeverity;
import com.internship.tool92.entity.IncidentStatus;
import com.internship.tool92.exception.ResourceNotFoundException;
import com.internship.tool92.repository.IncidentRepository;
import com.internship.tool92.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    void setUp() {
        incident = Incident.builder()
            .id(1L)
            .title("Test Incident")
            .description("Test Description")
            .status(IncidentStatus.OPEN)
            .severity(IncidentSeverity.HIGH)
            .build();
    }

    // Test 1
    @Test
    void getAllIncidents_ShouldReturnList() {
        when(incidentRepository.findAll()).thenReturn(List.of(incident));
        List<Incident> result = incidentService.getAllIncidents();
        assertEquals(1, result.size());
        verify(incidentRepository, times(1)).findAll();
    }

    // Test 2
    @Test
    void getIncidentById_ShouldReturnIncident_WhenExists() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));
        Incident result = incidentService.getIncidentById(1L);
        assertNotNull(result);
        assertEquals("Test Incident", result.getTitle());
    }

    // Test 3
    @Test
    void getIncidentById_ShouldThrow_WhenNotFound() {
        when(incidentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
            () -> incidentService.getIncidentById(99L));
    }

    // Test 4
    @Test
    void createIncident_ShouldSave_WhenValid() {
        when(incidentRepository.save(incident)).thenReturn(incident);
        Incident result = incidentService.createIncident(incident);
        assertNotNull(result);
        verify(incidentRepository, times(1)).save(incident);
    }

    // Test 5
    @Test
    void createIncident_ShouldThrow_WhenTitleEmpty() {
        incident.setTitle("");
        assertThrows(IllegalArgumentException.class,
            () -> incidentService.createIncident(incident));
    }

    // Test 6
    @Test
    void createIncident_ShouldThrow_WhenSeverityNull() {
        incident.setSeverity(null);
        assertThrows(IllegalArgumentException.class,
            () -> incidentService.createIncident(incident));
    }

    // Test 7
    @Test
    void updateIncident_ShouldUpdate_WhenExists() {
        Incident updated = Incident.builder()
            .title("Updated Title")
            .status(IncidentStatus.IN_PROGRESS)
            .severity(IncidentSeverity.MEDIUM)
            .build();
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);
        Incident result = incidentService.updateIncident(1L, updated);
        assertNotNull(result);
        verify(incidentRepository, times(1)).save(any(Incident.class));
    }

    // Test 8
    @Test
    void deleteIncident_ShouldDelete_WhenExists() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));
        doNothing().when(incidentRepository).delete(incident);
        incidentService.deleteIncident(1L);
        verify(incidentRepository, times(1)).delete(incident);
    }

    // Test 9
    @Test
    void getIncidentsByStatus_ShouldReturnList() {
        when(incidentRepository.findByStatus(IncidentStatus.OPEN))
            .thenReturn(List.of(incident));
        List<Incident> result = incidentService
            .getIncidentsByStatus(IncidentStatus.OPEN);
        assertEquals(1, result.size());
    }

    // Test 10
    @Test
    void getIncidentsByUser_ShouldReturnList() {
        when(incidentRepository.findByCreatedById(1L))
            .thenReturn(List.of(incident));
        List<Incident> result = incidentService.getIncidentsByUser(1L);
        assertEquals(1, result.size());
    }
}