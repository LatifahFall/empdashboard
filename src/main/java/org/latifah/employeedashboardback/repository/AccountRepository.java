package org.latifah.employeedashboardback.repository;

import org.latifah.employeedashboardback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    @Query("SELECT a FROM Account a WHERE LOWER(a.rawAccountNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Account> findByRawAccountNumberLike(@Param("query") String query);

}