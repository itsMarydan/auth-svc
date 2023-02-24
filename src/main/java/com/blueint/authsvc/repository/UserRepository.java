package com.blueint.authsvc.repository;


import com.blueint.authsvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUserId(int userId);
    List<User> findAll();
    List<User> findAllByActive(Boolean active);
    List<User> findAllByLocked(Boolean locked);
}
