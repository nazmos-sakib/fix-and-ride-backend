package com.api.fix_and_ride.dto;

import com.api.fix_and_ride.security.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupRequestDTO {
    @NotBlank public String firstName;
    @NotBlank public String lastName;
    @NotBlank public String address;

    @NotBlank(message = "House number code cannot be blank")
    /*@Pattern(
            regexp = "^\\d+$",
            message = "house number must be digits"
    )*/ public String houseNo;

    //99084-99089 ifconfig | grep -i 192
    @NotBlank(message = "ZIP code cannot be blank")
    @Pattern(
            //regexp = "^[0-9]{5}$",
            regexp = "^9908[4-9]$",
            message = "ZIP code must be between 99084-99089"
    )
    @Pattern(
            regexp = "^[0-9]{5}$",
            message = "ZIP code must be 5 digits"
    )  public String post;
    @NotBlank public String city;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    public String email;

    @NotBlank(message = "Password cannot be blank")
/*    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter and one digit"
    )*/
    @ValidPassword
    public String password;
}