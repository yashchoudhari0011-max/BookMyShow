package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.UserDto;
import com.cfs.bookmyshow.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Getter
    @Setter
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private String phoneNumber;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterRequest request) {
        UserDto userDto = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber()
        );
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
