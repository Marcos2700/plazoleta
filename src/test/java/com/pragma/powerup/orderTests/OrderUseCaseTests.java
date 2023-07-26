package com.pragma.powerup.orderTests;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OrderUseCaseTests {

    @InjectMocks
    OrderUseCase orderUseCase;

    @Mock
    IOrderPersistencePort orderPersistencePort;

    @Test
    void saveOrder(){
        Order order = new Order();

        Mockito.when(orderPersistencePort.saveOrder(order)).thenReturn(order);

        Order returnedOrder = orderUseCase.saveOrder(order);

        Assertions.assertEquals(order, returnedOrder);
    }

}
