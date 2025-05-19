package org.latifah.employeedashboardback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import org.latifah.employeedashboardback.model.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "Tel")
    private String Tel;

    @Column(name = "Birth_Date")
    private LocalDate Birth_Date;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "compte_bloque")
    private Boolean compteBloque;
    @Column(name = "documents_complets")
    private Boolean documentsComplets;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> servicesActifs = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Account> accounts;

    public boolean isCompteBloque() { return compteBloque; }
    public boolean isDocumentsComplets() { return documentsComplets; }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        this.Tel=tel;
    }

    public LocalDate getBirth_Date() {
        return Birth_Date;
    }

    public void setBirth_Date(LocalDate birth_Date) {
        this.Birth_Date = birth_Date;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }

    public List<String> getServicesActifs() { return servicesActifs; }
    public void setServicesActifs(List<String> servicesActifs) { this.servicesActifs = servicesActifs; }

    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

    public Boolean getCompteBloque() { return compteBloque; }
    public void setCompteBloque(boolean compteBloque) {
        this.compteBloque = compteBloque;
    }

    public Boolean getDocumentsComplets() { return documentsComplets; }
    public void setDocumentsComplets(boolean documentsComplets) {
        this.documentsComplets = documentsComplets;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", Tel='" + Tel + '\'' + ", Birth_Date=" + Birth_Date + ", role=" + role +
                '}';
    }
}