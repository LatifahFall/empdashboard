package org.latifah.employeedashboardback.dto;

import java.util.List;

public class ClientSummaryDTO {
    private Long clientId;
    private String fullName;
    private List<AccountSummaryDTO> accounts;

    // constructeur, getters/setters
    public ClientSummaryDTO() {
        super();
    }
    public ClientSummaryDTO(Long clientId, String fullName, List<AccountSummaryDTO> accounts) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.accounts = accounts;
    }

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
    public List<AccountSummaryDTO> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<AccountSummaryDTO> accounts) {
        this.accounts = accounts;
    }
}
