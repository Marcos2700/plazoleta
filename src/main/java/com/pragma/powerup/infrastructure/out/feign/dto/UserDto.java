package com.pragma.powerup.infrastructure.out.feign.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String lastname;
    private String identityDocument;
    private String phoneNumber;
    private Date dateOfBirth;
    private String email;
    private String password;
    private RoleDto role;
}
