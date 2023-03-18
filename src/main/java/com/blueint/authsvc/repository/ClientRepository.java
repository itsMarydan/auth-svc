package com.blueint.authsvc.repository;


import com.blueint.authsvc.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByClientId(String clientId);



    @Query("SELECT c FROM Client c WHERE c.id = :id")
    Client findClientById(String id);

    void deleteByClientId(String clientId);
    List<Client> findAll();

}
