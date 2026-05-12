package com.internship.tool92.service;

import com.internship.tool92.entity.User;
import com.internship.tool92.exception.AuthenticationException;
import com.internship.tool92.exception.ResourceNotFoundException;
import com.internship.tool92.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(
                "User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new AuthenticationException("Email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new AuthenticationException("Password cannot be empty");
        }
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new AuthenticationException("Full name cannot be empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}