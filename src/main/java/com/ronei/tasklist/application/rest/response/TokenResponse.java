package com.ronei.tasklist.application.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TokenResponse {

    private String type;

    private String accessToken;
}
