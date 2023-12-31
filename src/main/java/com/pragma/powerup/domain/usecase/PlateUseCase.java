package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPlateServicePort;
import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.domain.spi.IPlatePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public void updatePlate(Plate plate) {
        platePersistencePort.updatePlate(plate);
    }

    @Override
    public Plate getPlate(Long id) {
        return platePersistencePort.getPlate(id);
    }

    @Override
    public Page<Plate> listPlate(Long idRestaurant, Long idCategory, Pageable pageable) {
        return platePersistencePort.listPlate(idRestaurant, idCategory, pageable);
    }
}
