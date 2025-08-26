package com.project.bank.user_service.controllers;

import com.project.bank.user_service.DTOs.*;
import com.project.bank.user_service.service.LoggingProducer;
import com.project.bank.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
//    @Autowired
//    public UserService service;
private final UserService service;
private final LoggingProducer loggingProducer;

    public UserController(UserService service, LoggingProducer loggingProducer) {
        this.service = service;
        this.loggingProducer = loggingProducer;
    }

//    ---------Register-------
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register (@Valid @RequestBody RegisterRequest req){
        loggingProducer.sendLog(req, "Request");

        UUID id;
        id = service.register(req);

        RegisterResponse response = new RegisterResponse(id, req.username.trim(),"User registered successfully");

        loggingProducer.sendLog(response, "Response");
        return response ;

    }

//    -------Login--------------
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req){
        loggingProducer.sendLog(req, "Request");

        ProfileResponse profile=service.login(req);
        LoginResponse response = new LoginResponse(profile.userId, profile.username);

        loggingProducer.sendLog(response, "Response");
        return response;
    }

//    --------Profile-----
    @GetMapping("/{userId}/profile")
    public ProfileResponse profile(@PathVariable UUID userId, @RequestHeader(name = "X-User-Id", required = false) String headerUserId){

        Map<String, String> request = new HashMap<>(); 
        request.put("userId: " , userId.toString());
        request.put("headerUserId", headerUserId);

        loggingProducer.sendLog(request, "Request");

        if (headerUserId == null || !headerUserId.equals(userId.toString())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unauthorized");
        }
        ProfileResponse response = service.getProfile(userId);

        loggingProducer.sendLog(response, "Response");
        return response;
    }

}
