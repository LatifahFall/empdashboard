package org.latifah.employeedashboardback.dto;

import org.latifah.employeedashboardback.entity.User;

public class ClientBasicDTO {
    private Long clientId;
    private String fullName;

    // getters & setters
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static ClientBasicDTO fromUser(User user) {
        ClientBasicDTO dto = new ClientBasicDTO();
        dto.setClientId(user.getId());
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        return dto;
    }
}

