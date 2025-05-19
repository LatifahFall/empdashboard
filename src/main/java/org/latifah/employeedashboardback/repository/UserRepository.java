package org.latifah.employeedashboardback.repository;

import org.latifah.employeedashboardback.entity.User;
import org.latifah.employeedashboardback.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = org.latifah.employeedashboardback.model.Role.CLIENT")
    long countClients();

    // Recherche par nom utilisateur ou numéro de compte (join avec Account)
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.accounts a " +
            "LEFT JOIN FETCH a.transactions t " +
            "WHERE LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(a.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsersWithAccountsAndTransactions(@Param("query") String query);

//    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.accounts WHERE u.role = :role")
    List<User> findUsersWithAccountsByRole(@Param("role") Role role);



}
