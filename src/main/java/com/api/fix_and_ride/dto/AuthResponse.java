package com.api.fix_and_ride.dto;


import com.api.fix_and_ride.model.User;

public class AuthResponse {
    public String token;
    private UserDTO user;

    public AuthResponse(String token, UserDTO userDTO) {
        this.token = token;
        this.user = userDTO;
    }

    public String getToken() { return token; }
    public UserDTO getUser() { return user; }
}
