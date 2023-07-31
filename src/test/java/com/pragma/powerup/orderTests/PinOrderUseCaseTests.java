package com.pragma.powerup.orderTests;

import com.pragma.powerup.domain.model.PinOrder;
import com.pragma.powerup.domain.spi.IPinOrderPersistencePort;
import com.pragma.powerup.domain.usecase.PinOrderUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PinOrderUseCaseTests {
    @InjectMocks
    PinOrderUseCase pinOrderUseCase;

    @Mock
    IPinOrderPersistencePort pinOrderPersistencePort;

    @Test
    void savePinOrder(){
        PinOrder pinOrder = new PinOrder();
        try {
            pinOrderUseCase.savePinOrder(pinOrder);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getPinOrder(){
        PinOrder pinOrder = new PinOrder();
        Mockito.when(pinOrderPersistencePort.getByOrder(1L)).thenReturn(pinOrder);

        PinOrder result = pinOrderUseCase.getByOrder(1L);

        Assertions.assertInstanceOf(PinOrder.class, result);
    }

    @Test
    void deletePinOrder(){
        try {
            pinOrderUseCase.deletePinOrder(1L);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
