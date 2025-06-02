package org.latifah.employeedashboardback.controller;

import org.latifah.employeedashboardback.service.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("/test")
    public String testLog() {
        Map<String, Object> details = new HashMap<>();
        details.put("test", "manual");
        auditService.logAction("TEST", "SYSTEM", "0", details, true);
        return "Test OK";
    }

    //enregistre dans l'audit de tout ce qui est fait sous Service
    @GetMapping("/audit/test")
    public String test() {
        auditService.testInsert();
        return "Audit is done";
    }

}

