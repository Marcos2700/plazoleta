package com.pragma.powerup.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPlate {
    private Long idOrder;
    private Long idPlate;
    private Integer quantity;
}
