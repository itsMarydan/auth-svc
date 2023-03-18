package com.blueint.authsvc.service;


import com.blueint.authsvc.model.Client;
import com.blueint.authsvc.repository.ClientRepository;
import com.blueint.authsvc.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {


    private  final ClientRepository clientRepository;


    @Override
    public Client saveClient(Client client) {
        log.info("Saving new client {} to the database", client.getClientId());


        String clientSecret = TokenGenerator.generateToken();
        String encodedClientSecret = new BCryptPasswordEncoder().encode(clientSecret);
        log.info("Generated client secret: {}", clientSecret);
        log.info("Encoded client secret: {}", encodedClientSecret);
        client.setAuthorizationGrantTypes(generateClientAuthorizationGrantTypes());
        client.setClientSecret(encodedClientSecret);
        log.info("Saving client {} to the database", client.getClientId());
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(String id) {
        log.info("Fetching client {}", id);
        return clientRepository.findClientById(id);
    }

    @Override
    public Client getClient(String clientId) {
        log.info("Fetching client {}", clientId);
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public void deleteClient(String clientId) {
        log.info("Deleting client {}", clientId);
        clientRepository.findByClientId(clientId);
    }

    @Override
    public List<Client> getClients() {
        log.info("Fetching all clients");
        return clientRepository.findAll();
    }

    @Override
    public void addClientAuthorizationGrantTypes(String clientId, String grantType) {

    }

    @Override
    public Set<AuthorizationGrantType> generateClientAuthorizationGrantTypes() {
        log.info("Creating grant type object{} to client {}");
        Set<AuthorizationGrantType> grantTypes = Set.of(
                AuthorizationGrantType.AUTHORIZATION_CODE,
                AuthorizationGrantType.CLIENT_CREDENTIALS,
                AuthorizationGrantType.REFRESH_TOKEN,
                AuthorizationGrantType.JWT_BEARER
        );

        return grantTypes;
    }


    @Override
    public void addClientScopes(String clientId, String scope) {

    }

    @Override
    public void addClientRedirectUris(String clientId, String redirectUri) {

    }

    @Override
    public void addClientAuthorities(String clientId, String authority) {

    }
}
