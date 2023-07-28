package com.pragma.powerup.infrastructure.input.feign.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClientMessageDto {
    private Long id;
    private String name;
    private String lastname;
    private String identityDocument;
    private String phoneNumber;
    private Date dateOfBirth;
    private String email;
    private String password;
    private String pin;
}
