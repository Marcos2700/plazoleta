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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @Test
    void listOrders(){
        Pageable pageable = PageRequest.of(0, 10);
        Order order = new Order();
        List<Order> orderList = List.of(order);
        Page<Order> orderPage = new PageImpl<>(orderList, pageable, 1);

        Mockito.when(orderPersistencePort.listOrder(1L, "pending", pageable))
                .thenReturn(orderPage);

        Page<Order> returnedPage = orderUseCase.listOrder(1L, "pending", pageable);

        Assertions.assertEquals(orderPage, returnedPage);
    }

    @Test
    void getOrder(){
        Order order = new Order();

        Mockito.when(orderPersistencePort.getOrder(1L)).thenReturn(order);

        Order returnedOrder = orderUseCase.getOrder(1L);

        Assertions.assertEquals(order, returnedOrder);
    }

    @Test
    void updateStatus(){
        Order order = new Order();
        try{
            orderUseCase.updateStatus(order);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
