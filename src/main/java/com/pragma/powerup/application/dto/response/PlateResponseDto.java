package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.application.dto.CategoryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlateResponseDto {

    private String name;
    private CategoryDto category;
    private String description;
    private Integer price;
    private Long idRestaurant;
    private String urlImage;
    private Boolean active;
}
