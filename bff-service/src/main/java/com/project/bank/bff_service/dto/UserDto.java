package com.project.bank.bff_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    public UUID userId;
    public String username;
    public String email;
    public String firstName;
    public String lastName;

}
