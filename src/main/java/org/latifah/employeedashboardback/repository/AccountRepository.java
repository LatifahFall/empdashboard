package org.latifah.employeedashboardback.repository;

import org.latifah.employeedashboardback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
