package org.latifah.employeedashboardback.service;

import org.latifah.employeedashboardback.dto.ServiceSuspensionRequest;
import org.latifah.employeedashboardback.entity.SuspendedService;
import org.latifah.employeedashboardback.entity.User;
import org.latifah.employeedashboardback.repository.SuspendedServiceRepository;
import org.latifah.employeedashboardback.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuspensionService {

    private final UserRepository userRepository;
    private final SuspendedServiceRepository suspendedServiceRepository;

    public SuspensionService(UserRepository userRepository, SuspendedServiceRepository suspendedServiceRepository) {
        this.userRepository = userRepository;
        this.suspendedServiceRepository = suspendedServiceRepository;
    }

    public void suspendServices(ServiceSuspensionRequest request) {
        User user = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        for (String service : request.getServicesToSuspend()) {
            SuspendedService suspendedService = new SuspendedService();
            suspendedService.setUser(user);
            suspendedService.setServiceName(service);
            suspendedService.setReason(request.getReason());
            suspendedService.setNotificationMessage(request.getNotificationMessage());
            suspendedServiceRepository.save(suspendedService);
        }

        //notification logic ici
    }

    public List<SuspendedService> getSuspendedServices(Long clientId) {
        return suspendedServiceRepository.findByUserId(clientId);
    }
}

