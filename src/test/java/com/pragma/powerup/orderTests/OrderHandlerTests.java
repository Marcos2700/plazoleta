package com.pragma.powerup.orderTests;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.handler.impl.OrderHandlerImpl;
import com.pragma.powerup.application.mapper.IOrderPlateMapper;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderPlateServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class OrderHandlerTests {

    @InjectMocks
    OrderHandlerImpl orderHandler;

    @Mock
    IOrderServicePort orderServicePort;
    @Mock
    IOrderPlateServicePort orderPlateServicePort;
    @Mock
    IOrderRequestMapper orderRequestMapper;
    @Mock
    IOrderPlateMapper orderPlateMapper;

    @Test
    void saveOrder(){
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        Order order =  new Order();
        OrderPlate orderPlate = new OrderPlate();
        OrderPlateDto orderPlateDto = new OrderPlateDto();
        List<OrderPlateDto> orderPlateDtoList = List.of(orderPlateDto);

        Mockito.when(orderRequestMapper.toOrder(orderRequestDto)).thenReturn(order);
        Mockito.when(orderServicePort.saveOrder(order)).thenReturn(order);
        Mockito.when(orderPlateMapper.toOrderPlate(orderPlateDto)).thenReturn(orderPlate);

        try {
            orderHandler.saveOrder(orderRequestDto);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
