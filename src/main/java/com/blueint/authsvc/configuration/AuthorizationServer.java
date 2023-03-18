package com.blueint.authsvc.configuration;


import com.blueint.authsvc.model.Client;
import com.blueint.authsvc.service.ClientService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServer {

    private final ClientService clientService;

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login"))
                )
                // Accept access tokens for User Info and/or Client Registration
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
                .oauth2ResourceServer().jwt();

        return http.build();
    }


//    @Bean
//    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                // Form login handles the redirect to the login page from the
//                // authorization server filter chain
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new RegisteredClientRepository() {
            @Override
            public void save(RegisteredClient registeredClient) {
                Client client = new Client();
                client.setClientId(registeredClient.getClientId());
                client.setClientSecret(registeredClient.getClientSecret());
                client.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods());
                client.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes());
                client.setRedirectUrisAsSet(registeredClient.getRedirectUris());
                client.setScopes(registeredClient.getScopes());
                client.setClientSettings(registeredClient.getClientSettings());
                clientService.saveClient(client);
            }

            @Override
            public RegisteredClient findById(String id) {
                Client client = clientService.getClientById(id);
                if(client != null)
                    return toRegisteredClient(client);
                return null;
            }

            @Override
            public RegisteredClient findByClientId(String clientId) {
                Client client = clientService.getClient(clientId);
                if(client != null)
                    return toRegisteredClient(client);
                return null;
            }
        };
    }



    private RegisteredClient toRegisteredClient(Client entity) {
        return RegisteredClient.withId(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientAuthenticationMethods((Consumer<Set<ClientAuthenticationMethod>>) entity.getClientAuthenticationMethods())
                .authorizationGrantTypes((Consumer<Set<AuthorizationGrantType>>) entity.getClientAuthenticationMethods())
                .redirectUris((Consumer<Set<String>>) entity.getRedirectUris())
                .scopes((Consumer<Set<String>>) entity.getScopes())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}
