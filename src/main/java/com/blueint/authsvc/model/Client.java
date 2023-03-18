package com.blueint.authsvc.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Table(name = "`client`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.blueint.authsvc.utils.UuidGenerator")
    @Column(length = 36, nullable = false, unique = true, name = "id")
    private String id;
    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientName;

    private String clientDescription;

    @Column(nullable = false)
    private String clientSecret;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "registered_client_authentication_methods", joinColumns = @JoinColumn(name = "client_id"))
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "registered_client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @Column(nullable = false)
    @ElementCollection
    @CollectionTable(name = "registered_client_redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    private Set<String> redirectUris;

    @ElementCollection
    @CollectionTable(name = "registered_client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes = new HashSet<>();


    private ClientSettings clientSettings;


    public void setRedirectUris(String mySetString) {
        String[] values = mySetString.split(",");
        Set<String> redirectUris = new HashSet<>(Arrays.asList(values));
        this.redirectUris = redirectUris;
    }

    public void setRedirectUrisAsSet(Set<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

//  Todo:  private String authorizedGrantTypes;
//  Todo:   private String autoApproveScopes;
//  Todo:  private String registeredRedirectUri;
//  Todo:  private String webServerRedirectUri;
//  Todo:  private String authorities;
//  Todo: private Integer accessTokenValidity;
//  Todo: private Integer refreshTokenValidity;
//   Todo: private String additionalInformation;

}


