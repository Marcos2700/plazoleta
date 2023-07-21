package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlateRequestDto {
    private Long id;
    private String name;
    private Long idCategory;
    private String description;
    private Integer price;
    private Long idRestaurant;
    private String urlImage;
    private Boolean active;
}
