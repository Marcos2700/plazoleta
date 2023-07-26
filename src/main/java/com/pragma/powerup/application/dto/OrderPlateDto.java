package com.pragma.powerup.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPlateDto {
    private Long idOrder;
    private Long idPlate;
    private Integer quantity;
}
