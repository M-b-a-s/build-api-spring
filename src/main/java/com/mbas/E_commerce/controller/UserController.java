package com.mbas.E_commerce.controller;

import com.mbas.E_commerce.dto.RegisterUserRequest;
import com.mbas.E_commerce.dto.UpdateUserDto;
import com.mbas.E_commerce.dto.UserDto;
import com.mbas.E_commerce.entities.User;
import com.mbas.E_commerce.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found with id: " + id);
            errorResponse.put("status", "404");
            errorResponse.put("timestamp", Instant.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return ResponseEntity.ok(userDto);

    }

    @PostMapping
    public ResponseEntity<?> registerUser(
        @Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
        return ResponseEntity.badRequest().body(
                Map.of("email", "Email is already in use."));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable(name = "id") Long id,
        @RequestBody UpdateUserDto request) {
            var user = userRepository.findById(id).orElse(null);
            // check if user exists
            if(user == null) {
                return ResponseEntity.notFound().build();
            }

            user.setName(request.getName());
            user.setEmail(request.getEmail());

            userRepository.save(user);
            return ResponseEntity.ok(new UserDto(user.getId(), user.getName(), user.getEmail()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();

    }


}
