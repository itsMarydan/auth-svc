package com.blueint.authsvc.service;

import com.blueint.authsvc.model.Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.List;
import java.util.Set;

public interface ClientService {

        Client saveClient(Client client);

        Client getClientById(String id);
        Client getClient(String clientId);

        void deleteClient(String clientId);
        List<Client> getClients();

        void addClientAuthorizationGrantTypes(String clientId, String grantType);

        Set<AuthorizationGrantType> generateClientAuthorizationGrantTypes();


        void addClientScopes(String clientId, String scope);
        void addClientRedirectUris(String clientId, String redirectUri);
        void addClientAuthorities(String clientId, String authority);

}
