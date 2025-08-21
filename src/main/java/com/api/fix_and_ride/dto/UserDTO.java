package com.api.fix_and_ride.dto;

import com.api.fix_and_ride.model.User;

public record UserDTO(Long id, String username, String email, String address, String houseNo, String post, String city) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(),
                user.getAddress(), user.getHouseNo(), user.getPost(), user.getCity());
    }
}

