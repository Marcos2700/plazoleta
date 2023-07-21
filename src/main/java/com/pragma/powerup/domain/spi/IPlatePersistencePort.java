package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Plate;

import java.util.List;

public interface IPlatePersistencePort {

    void savePlate(Plate plate);

    List<Plate> getAllPlate();

    void updatePlate(Plate plate);

    Plate getPlate(Long id);
}
