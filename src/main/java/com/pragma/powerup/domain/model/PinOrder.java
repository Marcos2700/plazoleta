package com.pragma.powerup.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinOrder {
    private Long id;
    private String pin;
    private Long idOrder;
}
