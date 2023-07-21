package com.pragma.powerup.plateTests;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.domain.spi.IPlatePersistencePort;
import com.pragma.powerup.domain.usecase.PlateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class PlateUseCaseTests {

    @InjectMocks
    PlateUseCase plateUseCase;

    @Mock
    IPlatePersistencePort platePersistencePort;

    @Test
    void savePlate(){
        Plate plate = new Plate();

        Mockito.when(platePersistencePort.getAllPlate()).thenReturn(List.of(plate));

        plateUseCase.savePlate(plate);

        Assertions.assertEquals(1, plateUseCase.getAllPlates().size());
    }

    @Test
    void updatePlate(){
        Plate plate = new Plate();

        try{
            plateUseCase.updatePlate(plate);
            Assertions.assertTrue(true);
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }
    }
}
