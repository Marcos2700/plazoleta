package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Plate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPlateServicePort {

    void savePlate(Plate plate);

    List<Plate> getAllPlates();

    void updatePlate(Plate plate);

    Plate getPlate(Long id);

    Page<Plate> listPlate(Long idRestaurant, Long idCategory, Pageable pageable);
}
