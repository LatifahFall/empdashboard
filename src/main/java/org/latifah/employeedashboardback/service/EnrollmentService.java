package org.latifah.employeedashboardback.service;

import org.latifah.employeedashboardback.dto.*;
import org.latifah.employeedashboardback.entity.*;
import org.latifah.employeedashboardback.model.Role;
import org.latifah.employeedashboardback.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public EnrollmentService(UserRepository userRepository,
                             AccountRepository accountRepository,
                             TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void enrollClient(EnrollmentRequest dto) {
        User client = new User();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setTel(dto.getTel());
        client.setBirth_Date(LocalDate.parse(dto.getBirthDate()));
        client.setRole(Role.CLIENT);

        userRepository.save(client);

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        account.setUser(client);

        accountRepository.save(account);
    }

    @Transactional
    public boolean updateClient(ClientUpdateRequest dto) {
        Optional<User> opt = userRepository.findById(dto.getClientId());
        if (opt.isEmpty()) return false;

        if (!"supervisor-password".equals(dto.getSupervisorPassword())) return false;

        User client = opt.get();
        if (dto.getNewFirstName() != null) client.setFirstName(dto.getNewFirstName());
        if (dto.getNewLastName() != null) client.setLastName(dto.getNewLastName());
        if (dto.getNewEmail() != null) client.setEmail(dto.getNewEmail());
        if (dto.getNewTel() != null) client.setTel(dto.getNewTel());

        userRepository.save(client); // save() fonctionne pour update aussi
        return true;
    }

    @Transactional
    public boolean deleteClient(Long clientId) {
        Optional<User> opt = userRepository.findById(clientId);
        if (opt.isEmpty()) return false;

        if (!transactionRepository.findByClientId(clientId).isEmpty()) return false;

        User client = opt.get();
        accountRepository.deleteByUserId(clientId); // méthode custom ou native query
        userRepository.delete(client);
        return true;
    }
}
