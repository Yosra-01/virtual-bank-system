package com.project.bank.user_service.service;

import com.project.bank.user_service.DTOs.LoginRequest;
import com.project.bank.user_service.DTOs.ProfileResponse;
import com.project.bank.user_service.DTOs.RegisterRequest;
import com.project.bank.user_service.model.User;
import com.project.bank.user_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public UUID register (RegisterRequest user){

        String username= user.username.trim();
        String email=user.email.trim().toLowerCase(Locale.ROOT);

        if(repo.existsByUsernameIgnoreCase(username) || repo.existsByEmailIgnoreCase(email)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Username or email already exists");
        }

        User u= new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setFirstName(user.firstName.trim());
        u.setLastName(user.lastName.trim());
        u.setPasswordHash(encoder.encode(user.password));


        repo.save(u);
        return u.getId();

    }

    public ProfileResponse login(LoginRequest login){
        String username=login.username.trim();
        User u=repo.findByUsernameIgnoreCase(username).orElseThrow
                (()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!encoder.matches(login.password, u.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return new ProfileResponse(u.getId(),u.getUsername(),u.getEmail(),u.getFirstName(),u.getLastName());
    }
    public ProfileResponse getProfile(UUID userId) {
        User u = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new ProfileResponse(u.getId(), u.getUsername(), u.getEmail(), u.getFirstName(), u.getLastName());
    }




}
