package org.latifah.employeedashboardback.controller;

import org.latifah.employeedashboardback.dto.ClientDeletionRequest;
import org.latifah.employeedashboardback.dto.ClientUpdateRequest;
import org.latifah.employeedashboardback.dto.EnrollmentRequest;
import org.latifah.employeedashboardback.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeDashboardController {

    @Autowired
    private EnrollmentService service;

    @GetMapping("/clients/count")
    public long countClients() {
        return service.countClients();
    }

    @GetMapping("/accounts/count")
    public long countAccounts() {
        return service.countAccounts();
    }

    @PostMapping("/enroll")
    public void enroll(@RequestBody EnrollmentRequest req) {
        service.enrollClient(req);
    }

    @PutMapping("/update")
    public void update(@RequestBody ClientUpdateRequest req) {
        boolean ok = service.updateClient(req);
        if (!ok) {
            throw new RuntimeException("Update failed: invalid supervisor password or client not found.");
        }
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody ClientDeletionRequest req) {
        boolean ok = service.deleteClient(req.getClientId());
        if (!ok) {
            throw new RuntimeException("Deletion failed: client has active transactions or does not exist.");
        }
    }
}
