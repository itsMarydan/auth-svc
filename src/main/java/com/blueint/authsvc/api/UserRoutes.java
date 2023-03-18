package com.blueint.authsvc.api;


import com.blueint.authsvc.model.User;
import com.blueint.authsvc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRoutes {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<User>>getUsers(){

        log.info("GET /api/v1/users");
        return ResponseEntity.ok().body(userService.getUsers());
    }

}
