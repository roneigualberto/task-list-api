package com.ronei.tasklist.application.rest;


import com.ronei.tasklist.application.rest.request.UserRequest;
import com.ronei.tasklist.application.rest.response.UserResponse;
import com.ronei.tasklist.domain.user.User;
import com.ronei.tasklist.domain.user.UserForm;
import com.ronei.tasklist.domain.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final UserService userService;


    @Transactional
    @PostMapping()
    public ResponseEntity<UserResponse> createAccount(@RequestBody UserRequest request) {

        UserForm form = request.toUserForm();

        User userSaved = userService.createUser(form);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId())
                .toUri();

        UserResponse response = UserResponse.fromUser(userSaved);

        return ResponseEntity.created(location).body(response);
    }
}
