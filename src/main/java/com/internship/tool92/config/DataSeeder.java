package com.internship.tool92.config;

import com.internship.tool92.entity.*;
import com.internship.tool92.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (incidentRepository.count() > 0) {
            log.info("Data already seeded. Skipping.");
            return;
        }

        log.info("Seeding data...");

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseGet(() -> roleRepository.save(
                Role.builder().name(RoleName.ROLE_USER).build()));

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
            .orElseGet(() -> roleRepository.save(
                Role.builder().name(RoleName.ROLE_ADMIN).build()));

        User admin = User.builder()
            .email("admin@tool92.com")
            .password(passwordEncoder.encode("Admin@123"))
            .fullName("Admin User")
            .roles(Set.of(adminRole))
            .build();

        User user1 = User.builder()
            .email("user1@tool92.com")
            .password(passwordEncoder.encode("User@123"))
            .fullName("Test User One")
            .roles(Set.of(userRole))
            .build();

        if (!userRepository.existsByEmail(admin.getEmail())) {
            userRepository.save(admin);
        }
        if (!userRepository.existsByEmail(user1.getEmail())) {
            userRepository.save(user1);
        }

        User seededAdmin = userRepository.findByEmail("admin@tool92.com")
            .orElse(admin);

        String[] titles = {
            "Server Down", "DB Connection Failed", "High CPU Usage",
            "Memory Leak Detected", "API Timeout", "Login Failure Spike",
            "Disk Space Critical", "Network Latency High", "SSL Cert Expiring",
            "Unauthorized Access Attempt", "Payment Gateway Error",
            "Cache Miss Rate High", "Email Service Down", "Queue Backlog",
            "Deployment Failed", "Backup Job Failed", "DNS Resolution Error",
            "Load Balancer Issue", "Microservice Crash", "Data Sync Failure",
            "Security Scan Alert", "Rate Limit Breached", "Config Drift Detected",
            "Health Check Failing", "Log Volume Spike", "Token Expiry Issue",
            "Service Degraded", "Replica Lag High", "Firewall Rule Violation",
            "CDN Cache Purge Failed"
        };

        IncidentStatus[] statuses = IncidentStatus.values();
        IncidentSeverity[] severities = IncidentSeverity.values();

        for (int i = 0; i < 30; i++) {
            Incident incident = Incident.builder()
                .title(titles[i])
                .description("Auto-seeded incident: " + titles[i])
                .status(statuses[i % statuses.length])
                .severity(severities[i % severities.length])
                .createdBy(seededAdmin)
                .build();
            incidentRepository.save(incident);
        }

        log.info("Seeded 30 incidents successfully.");
    }
}