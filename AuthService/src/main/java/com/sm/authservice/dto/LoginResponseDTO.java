package com.sm.authservice.dto;

public class LoginResponseDTO {
    private final String token;

    // Since there is only 1 property, use constructor to set it (cleaner approach)
    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
