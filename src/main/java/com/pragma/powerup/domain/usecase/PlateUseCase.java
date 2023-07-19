package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPlateServicePort;
import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.domain.spi.IPlatePersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlateUseCase implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;
    @Override
    public void savePlate(Plate plate) {
        platePersistencePort.savePlate(plate);
    }

    @Override
    public List<Plate> getAllPlates() {
        return platePersistencePort.getAllPlate();
    }
}
