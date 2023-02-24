package com.blueint.authsvc.repository;

import com.blueint.authsvc.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

        Group findByName(String name);
        Group findByGroupId(Long groupId);

        List<Group> findAll();
}
