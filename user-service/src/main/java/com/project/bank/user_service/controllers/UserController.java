package com.project.bank.user_service.controllers;

import com.project.bank.user_service.DTOs.*;
import com.project.bank.user_service.DTOs.*;
import com.project.bank.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
//    @Autowired
//    public UserService service;
private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    ---------Register-------
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register (@Valid @RequestBody RegisterRequest req){
        UUID id;
        id = service.register(req);
        return new RegisterResponse(id, req.username.trim(),"User registered successfully");

    }

//    -------Login--------------
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req){
        ProfileResponse profile=service.login(req);
        return new LoginResponse(profile.userId, profile.username);
    }

//    --------Profile-----
    @GetMapping("{userId}/profile")
    public ProfileResponse profile(@PathVariable UUID userId, @RequestHeader(name = "X-User-Id", required = false) String headerUserId){
        if (headerUserId == null || !headerUserId.equals(userId.toString())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unauthorized");
        }
        return service.getProfile(userId);
    }

}
