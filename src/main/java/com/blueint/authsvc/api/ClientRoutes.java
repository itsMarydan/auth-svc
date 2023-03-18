package com.blueint.authsvc.api;


import com.blueint.authsvc.model.Client;
import com.blueint.authsvc.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientRoutes {


    private final ClientService clientService;


    @GetMapping
    public ResponseEntity<?> getClients(){
        log.info("GET /api/v1/clients");
        try{
           log.info("Retrieving clients from database...");
           List<Client> clients = clientService.getClients();
              log.info("Retrieved clients from database.");
              log.info("Clients: {}", clients);
              log.info("Clients size: {}", clients.size());
              log.info("Returning Clients: {}", clients);

            return ResponseEntity.ok().body(clientService.getClients());

        } catch (Exception e) {
            log.error("Error retrieving clients from database: {}", e.getStackTrace());
            return ResponseEntity.status(500).body("Error retrieving clients from database");
        }

    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@RequestBody Client client ){
        log.info("POST /api/v1/clients/create");
        log.info("Client: {}", client);
        try{
            log.info("Creating client...");
            Client createdClient = clientService.saveClient(client);
            log.info("Created client: {}", createdClient);
            log.info("Returning created client: {}", createdClient);
            return ResponseEntity.ok().body("Created client with id: " + createdClient.getClientId() + createdClient);
        } catch (Exception e) {
            log.error("Error creating client: {}", e.getStackTrace());
            return ResponseEntity.status(500).body("Error creating client: " + client.getClientId());
        }
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable("clientId") String clientId){
        log.info("DELETE /api/v1/clients/delete/{}", clientId);
        try{
            log.info("Deleting client...");
              clientService.deleteClient(clientId);
            log.info("Deleted client: {}", clientId);
            log.info("Returning deleted client success: {}", clientId);
            return ResponseEntity.ok().body("Deleted client: " + clientId);
        } catch (Exception e) {
            log.error("Error deleting client: {}", e.getStackTrace());
            return ResponseEntity.status(500).body("Error deleting client: " + clientId);
        }
    }


}
