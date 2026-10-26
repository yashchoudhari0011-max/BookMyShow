package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.UserDto;
import com.cfs.bookmyshow.entity.User;
import com.cfs.bookmyshow.exception.ResourceNotFoundException;
import com.cfs.bookmyshow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto registerUser(String name, String email, String password, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
