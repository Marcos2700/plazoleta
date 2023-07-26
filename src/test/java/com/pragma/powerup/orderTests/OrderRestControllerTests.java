package com.pragma.powerup.orderTests;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.infrastructure.input.rest.OrderRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OrderRestControllerTests {

    @InjectMocks
    OrderRestController orderRestController;

    @Mock
    IOrderHandler orderHandler;

    @Test
    void makeOrder(){
        OrderRequestDto orderRequestDto = new OrderRequestDto();

        ResponseEntity<Void> response = orderRestController.makeOrder(orderRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
