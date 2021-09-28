package com.ronei.tasklist.application.rest.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredentialRequest {


    private String username;

    private String password;

}
