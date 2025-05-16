package org.latifah.employeedashboardback.repository;

import org.latifah.employeedashboardback.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long clientId);
}
