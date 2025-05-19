package org.latifah.employeedashboardback.service;

import org.latifah.employeedashboardback.dto.*;
import org.latifah.employeedashboardback.entity.*;
import org.latifah.employeedashboardback.model.Role;
import org.latifah.employeedashboardback.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final UserRepository userRepository;

    public ClientService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ClientSummaryDTO> getClientsWithAccountsAndTransactions(String role) {
//        List<User> users = userRepository.findUsersWithAccountsByRole(role);
        List<User> users = userRepository.findUsersWithAccountsByRole(Role.CLIENT);

        // Test rapide pour debug
        System.out.println("Users found: " + users.size());
        for (User u : users) {
            System.out.println("User: " + u.getFirstName() + " " + u.getLastName() + ", Accounts: " + u.getAccounts().size());
        }

        //transformation en dto
        List<ClientSummaryDTO> dtos = users.stream()
                .map(user -> {
                    // Construire le nom complet
                    String fullName = user.getFirstName() + " " + user.getLastName();

                    // Convertir la liste des comptes User -> AccountSummaryDTO
                    List<AccountSummaryDTO> accountDTOs = user.getAccounts().stream()
                            .map(account -> {
                                AccountSummaryDTO accountDTO = new AccountSummaryDTO();
                                accountDTO.setAccountNumber(account.getId());
                                accountDTO.setAccountNumber(account.getAccountNumber());
                                accountDTO.setBalance(account.getBalance());
                                return accountDTO;
                            })
                            .collect(Collectors.toList());

                    // Construire et retourner le DTO client
                    return new ClientSummaryDTO(user.getId(), fullName, accountDTOs);
                })
                .collect(Collectors.toList());

        return dtos;
    }



//    public List<ClientSummaryDTO> getClientsWithAccountsAndTransactions(String searchQuery) {
//        List<User> clients = userRepository.findByRole(Role.CLIENT);
//
//        if (searchQuery != null) {
//            searchQuery = searchQuery.trim().toLowerCase();
//        }
//
//        List<ClientSummaryDTO> result = new ArrayList<>();
//
//        for (User client : clients) {
//            String fullName = (client.getFirstName() + " " + client.getLastName()).toLowerCase();
//            boolean matchClientName = (searchQuery == null || searchQuery.isEmpty()) || fullName.contains(searchQuery);
//
//            // filtrer les comptes selon searchQuery
//            String finalSearchQuery = searchQuery;
//            List<AccountSummaryDTO> matchingAccounts = client.getAccounts().stream()
//                    .filter(acc -> finalSearchQuery == null || finalSearchQuery.isEmpty()
//                            || acc.getAccountNumber().toLowerCase().contains(finalSearchQuery)
//                            || matchClientName)
//                    .map(acc -> {
//                        List<TransactionDTO> txs = acc.getTransactions().stream()
//                                .map(tx -> new TransactionDTO(tx.getId(), tx.getAmount(), tx.getDate()))
//                                .collect(Collectors.toList());
//                        return new AccountSummaryDTO(acc.getAccountNumber(), acc.getType(), acc.getBalance(), txs);
//                    })
//                    .collect(Collectors.toList());
//
//            if (!matchingAccounts.isEmpty() || matchClientName) {
//                result.add(new ClientSummaryDTO(client.getId(), client.getFirstName() + " " + client.getLastName(), matchingAccounts));
//            }
//        }
//
//        return result;
//    }
}

