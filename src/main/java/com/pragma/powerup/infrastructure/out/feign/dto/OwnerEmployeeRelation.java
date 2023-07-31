package com.pragma.powerup.infrastructure.out.feign.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerEmployeeRelation {
    private Long id;
    private Long idOwner;
    private Long idEmployee;
}
