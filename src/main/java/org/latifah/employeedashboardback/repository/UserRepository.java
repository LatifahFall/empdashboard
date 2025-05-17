package org.latifah.employeedashboardback.repository;

import org.latifah.employeedashboardback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = org.latifah.employeedashboardback.model.Role.CLIENT")
    long countClients();

}
