package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlateInfoResponseDto {

    private String name;
    private String description;
    private Integer price;
    private String urlImage;
    private Boolean activate;
}
