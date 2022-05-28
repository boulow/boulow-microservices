package com.boulow.tribe.utils;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserContext {
    public static final String CORRELATION_ID = "correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "userId";

    private String correlationId;
    private String authToken;
    private String userId;

}
