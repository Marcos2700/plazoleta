package com.pragma.powerup.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Order {

    private Long id;
    private Long idClient;
    private Date date;
    private String status;
    private Long idChef;
    private Long idRestaurant;
}
