package org.latifah.employeedashboardback.service;

import org.latifah.employeedashboardback.entity.AuditLog;
import org.latifah.employeedashboardback.entity.User;
import org.latifah.employeedashboardback.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void testInsert() {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(null);
        auditLog.setAction("TEST_INSERT");
        auditLog.setEntityType("TEST");
        auditLog.setEntityId("TEST_ID");
        auditLog.setDetails("{\"test\": \"manual\"}");
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setSuccess(true);
        logger.info("Testing audit log save: {}", auditLog);
        auditLogRepository.saveAndFlush(auditLog);
        logger.info("Test audit log saved");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAction(String action, String entityType, String entityId, Map<String, Object> details, boolean success) {
        logger.info("⏺️ Tentative de journalisation de l'action: [{}] sur [{}] avec ID [{}]", action, entityType, entityId);

        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setDetails(mapToJson(details));
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setSuccess(success);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                Object principal = auth.getPrincipal();
                if (principal instanceof org.springframework.security.core.userdetails.User userDetails) {
                    User user = userService.findByEmail(userDetails.getUsername());
                    if (user != null) {
                        auditLog.setUser(user);
                        logger.debug("👤 Utilisateur trouvé : {}", user.getEmail());
                    } else {
                        logger.warn("⚠️ Utilisateur non trouvé pour l'email : {}", userDetails.getUsername());
                    }
                } else {
                    logger.warn("⚠️ Principal inattendu dans le contexte de sécurité : {}", principal.getClass().getName());
                }
            } else {
                logger.warn("⚠️ Aucun utilisateur authentifié pour l'action : {}", action);
            }

            auditLogRepository.saveAndFlush(auditLog);
            logger.info("✅ Journal d'audit sauvegardé avec succès : Action=[{}], Entité=[{}], ID=[{}], Succès=[{}]",
                    action, entityType, entityId, success);
        } catch (Exception e) {
            logger.error("❌ Erreur lors de la sauvegarde du journal d'audit pour Action=[{}], Entité=[{}], ID=[{}] : {}",
                    action, entityType, entityId, e.getMessage(), e);
            throw new RuntimeException("Échec de la sauvegarde du journal d'audit", e);
        }
    }


    private String mapToJson(Map<String, Object> details) {
        if (details == null || details.isEmpty()) return "{}";
        StringBuilder json = new StringBuilder("{");
        details.forEach((k, v) -> json.append("\"").append(k).append("\":\"").append(v).append("\","));
        json.setLength(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    public Map<String, Object> getAuditStats() {
        long totalLogs = auditLogRepository.count();
        long successfulLogs = auditLogRepository.countBySuccessTrue();
        long failedLogs = totalLogs - successfulLogs;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLogs", totalLogs);
        stats.put("successfulLogs", successfulLogs);
        stats.put("failedLogs", failedLogs);
        return stats;
    }

    public long countBySuccessTrue() {
        return auditLogRepository.countBySuccessTrue();
    }
}