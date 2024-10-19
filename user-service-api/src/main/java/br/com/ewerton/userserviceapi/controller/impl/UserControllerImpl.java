package br.com.ewerton.userserviceapi.controller.impl;

import br.com.ewerton.userserviceapi.controller.UserController;
import br.com.ewerton.userserviceapi.entity.User;
import br.com.ewerton.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
