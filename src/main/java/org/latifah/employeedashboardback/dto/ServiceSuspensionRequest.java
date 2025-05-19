package org.latifah.employeedashboardback.dto;

import java.util.List;
import org.latifah.employeedashboardback.model.BankService;

public class ServiceSuspensionRequest {
    private Long clientId;
    private List<BankService> servicesToSuspend;
    private String reason;
    private String notificationMessage;

    // Getters et Setters
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public List<BankService> getServicesToSuspend() {
        return servicesToSuspend;
    }
        public void setServicesToSuspend(List<BankService> servicesToSuspend) {
        this.servicesToSuspend = servicesToSuspend;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
}

