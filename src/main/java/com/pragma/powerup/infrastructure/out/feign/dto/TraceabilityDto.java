package com.pragma.powerup.infrastructure.out.feign.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TraceabilityDto {
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private Date date;
    private String previousState;
    private String currentState;
    private Long idEmployee;
    private String emailEmployee;
}
