package org.latifah.employeedashboardback.dto;

import java.util.List;

public class ServiceSuspensionRequest {
    private Long clientId;
    private List<String> servicesToSuspend;
    private String reason;
    private String notificationMessage;

    // Getters et Setters
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public List<String> getServicesToSuspend() {
        return servicesToSuspend;
    }
    public void setServicesToSuspend(List<String> servicesToSuspend) {
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

