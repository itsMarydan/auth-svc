package com.blueint.authsvc.repository;

import com.blueint.authsvc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
    Role findByRoleId(Long roleId);
    List<Role> findAll();
}
