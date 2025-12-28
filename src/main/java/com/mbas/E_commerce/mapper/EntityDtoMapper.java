package com.mbas.E_commerce.mapper;

import com.mbas.E_commerce.dto.LoginRequest;
import com.mbas.E_commerce.dto.RegisterUserRequest;
import com.mbas.E_commerce.dto.UpdateUserDto;
import com.mbas.E_commerce.dto.UserDto;
import com.mbas.E_commerce.entities.User;

public class EntityDtoMapper {
    // user entity to user dto
    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    // register request dto to user entity
    public static User toUser(RegisterUserRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    // update an existing user from UpdateUserDto
    public static void updateUserFromDto(UpdateUserDto request, User user) {
        if (request == null || user == null) return;
        user.setName(request.getName());
        user.setEmail(request.getEmail());
    }

}
