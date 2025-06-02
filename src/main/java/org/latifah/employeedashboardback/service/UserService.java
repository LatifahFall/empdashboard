package org.latifah.employeedashboardback.service;

import org.latifah.employeedashboardback.model.Role;
import org.latifah.employeedashboardback.entity.User;
import org.latifah.employeedashboardback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }
    public Role getUserRoleByEmail(String email) {
        return userRepository.getUserRoleByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"))
                .getRole();
    }
    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }
}
