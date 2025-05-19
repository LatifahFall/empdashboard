package org.latifah.employeedashboardback.controller;

import org.latifah.employeedashboardback.dto.*;
import org.latifah.employeedashboardback.entity.SuspendedService;
import org.latifah.employeedashboardback.entity.User;
import org.latifah.employeedashboardback.repository.SuspendedServiceRepository;
import org.latifah.employeedashboardback.repository.UserRepository;
import org.latifah.employeedashboardback.service.ClientService;
import org.latifah.employeedashboardback.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeDashboardController {

    @Autowired
    private EnrollmentService service;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuspendedServiceRepository suspendedServiceRepository;


    @GetMapping("/clients/count")
    public long countClients() {
        return service.countClients();
    }

    @GetMapping("/accounts/count")
    public long countAccounts() {
        return service.countAccounts();
    }

    //endpoint for the simple list of all clients
    @GetMapping("/clients/basic")
    public List<ClientBasicDTO> listClientsBasic() {
        return clientService.getAllClientsBasic();
    }

    //endpoint for the list of all clients with details
    @GetMapping("/clients/detailed")
    public List<ClientSummaryDTO> listClients(@RequestParam(name = "search", required = false) String search) {
        return clientService.getClientsWithAccountsAndTransactions(search);
    }

    //endpoint to search client by name
    @GetMapping("/clients/search")
    public List<ClientSummaryDTO> searchClientsByName(@RequestParam("name") String name) {
        return clientService.searchClientsByName(name);
    }

    //endpoint pour afficher les details d'un client
    @GetMapping("/clients/{id}")
    public ClientSummaryDTO getClientDetails(@PathVariable("id") Long id) {
        return clientService.getClientWithDetails(id);
    }

    @PostMapping("/enroll")
    public void enroll(@RequestBody EnrollmentRequest req) {
        service.enrollClient(req);
    }

    @PutMapping("/update")
    public void update(@RequestBody ClientUpdateRequest req) {
        boolean ok = service.updateClient(req);
        if (!ok) {
            throw new RuntimeException("Update failed: invalid supervisor password or client not found.");
        }
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody ClientDeletionRequest req) {
        boolean ok = service.deleteClient(req.getClientId());
        if (!ok) {
            throw new RuntimeException("Deletion failed: client has active transactions or does not exist.");
        }
    }

    // Endpoint pour toggle compteBloque et documentsComplets
    @PutMapping("/clients/{id}/status")
    public ResponseEntity<String> updateClientStatus(
            @PathVariable("id") Long id,
            @RequestParam("compteBloque") Boolean compteBloque,
            @RequestParam("documentsComplets") Boolean documentsComplets) {

        try {
            clientService.updateClientStatus(id, compteBloque, documentsComplets);
            return ResponseEntity.ok("Statuts mis à jour avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //endpoint pour activer un service donné
    //exemple d'entree
//    {
//        "clientId": "3",
//            "services": ["virements", "acces_en_ligne"]
//    }
    //NB il faut que l'admin ajoute la liste des services ou bien que ca soit un enum
    @PostMapping("/clients/activer-services")
    public ResponseEntity<?> activerServicesPourClient(@RequestBody ActivateServicesRequest request) {
        try {
            User updatedUser = clientService.activerServices(request.getClientId(), request.getServices());
            return ResponseEntity.ok("Services activés avec succès pour " + updatedUser.getFirstName());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //endpoint pour la suspension d'un client
    //exemple d'entree:
    //{
    //  "servicesToSuspend": ["virements"],
    //  "reason": "non-paiement",
    //  "notificationMessage": "Vos virements et chéquier sont suspendus pour non-paiement."
    //}
    @PutMapping("/clients/{id}/suspend-services")
    public ResponseEntity<String> suspendServices(
            @PathVariable("id") Long id,
            @RequestBody ServiceSuspensionRequest request) {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        User user = userOptional.get();

        for (String service : request.getServicesToSuspend()) {
            SuspendedService suspended = new SuspendedService();
            suspended.setServiceName(service);
            suspended.setReason(request.getReason());
            suspended.setNotificationMessage(request.getNotificationMessage());
            suspended.setUser(user);

            user.getSuspendedServices().add(suspended);
        }

        userRepository.save(user);

        return ResponseEntity.ok("Services suspendus avec succès");
    }
}
