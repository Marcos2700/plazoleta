package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plate {
    private Long id;
    private String name;
    private Long idCategory;
    private String description;
    private Integer price;
    private Long idRestaurant;
    private String urlImage;
    private Boolean active;
}
