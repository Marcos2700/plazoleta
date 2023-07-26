package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.application.dto.OrderPlateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long idClient;
    private Date date;
    private String status;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderPlateDto> orders;
}
