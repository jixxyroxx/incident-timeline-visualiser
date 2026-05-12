package com.internship.tool92.config;

import com.internship.tool92.entity.Incident;
import com.internship.tool92.entity.IncidentStatus;
import com.internship.tool92.repository.IncidentRepository;
import com.internship.tool92.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ScheduledTasks {

    private final IncidentRepository incidentRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void sendDailyOpenIncidentReminder() {
        log.info("Running daily open incident reminder...");

        List<Incident> openIncidents = incidentRepository
            .findByStatus(IncidentStatus.OPEN);

        if (openIncidents.isEmpty()) {
            log.info("No open incidents found.");
            return;
        }

        for (Incident incident : openIncidents) {
            if (incident.getCreatedBy() != null
                    && incident.getCreatedBy().getEmail() != null) {
                emailService.sendIncidentNotification(
                    incident.getCreatedBy().getEmail(),
                    "Reminder: Open Incident - " + incident.getTitle(),
                    incident.getTitle(),
                    incident.getStatus().name(),
                    incident.getSeverity().name()
                );
            }
        }

        log.info("Daily reminder sent for {} open incidents.",
            openIncidents.size());
    }
}