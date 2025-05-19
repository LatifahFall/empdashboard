package org.latifah.employeedashboardback.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SuspendedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    private String reason;

    private String notificationMessage;

    private LocalDate suspensionDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters et Setters

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return reason;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setSuspensionDate(LocalDate suspensionDate) {
        this.suspensionDate = suspensionDate;
    }
    public LocalDate getSuspensionDate() {
        return suspensionDate;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}

