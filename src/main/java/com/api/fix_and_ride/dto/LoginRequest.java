package com.api.fix_and_ride.dto;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank public String email;
    @NotBlank public String password;
}
