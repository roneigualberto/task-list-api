package com.ronei.tasklist.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronei.tasklist.application.rest.request.UserRequest;
import com.ronei.tasklist.domain.user.User;
import com.ronei.tasklist.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIT {


    private static final String USER_EMAIL = "user@email.com";
    private static final String USER_FIRST_NAME = "User";
    private static final String USER_LAST_NAME = "User";
    private static final String USER_PASSWORD = "Password";
    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @Autowired
    private  UserRepository userRepository;


    @Test
    void shouldCreatedAccount() throws Exception {
        UserRequest request = UserRequest.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(USER_LAST_NAME));


        Optional<User> optUser = userRepository.findByEmail(USER_EMAIL);

        assertThat(optUser.isPresent()).isTrue();




    }


}