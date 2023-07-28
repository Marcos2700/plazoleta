package com.pragma.powerup.orderTests;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.infrastructure.input.rest.OrderRestController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ExtendWith(SpringExtension.class)
class OrderRestControllerTests {

    @InjectMocks
    OrderRestController orderRestController;

    @Mock
    IOrderHandler orderHandler;
    @Mock
    HttpServletRequest request;

    @Test
    void makeOrder(){
        OrderRequestDto orderRequestDto = new OrderRequestDto();

        ResponseEntity<Void> response = orderRestController.makeOrder(orderRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void listOrders(){
        Pageable pageable = PageRequest.of(0, 10);
        OrderInfoResponseDto orderInfoResponseDto = new OrderInfoResponseDto();
        List<OrderInfoResponseDto> orderInfoResponseDtoList = List.of(orderInfoResponseDto);
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = new PageImpl<>(orderInfoResponseDtoList, pageable, 1);

        Mockito.when(orderHandler.listOrder("pending", 0, 10, request))
                .thenReturn(orderInfoResponseDtoPage);

        ResponseEntity<Page<OrderInfoResponseDto>> response = orderRestController.listOrders(0, 10, "pending", request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(orderInfoResponseDtoPage, response.getBody());
    }

    @Test
    void updateOrderStatus(){
        Pageable pageable = PageRequest.of(0, 10);
        OrderInfoResponseDto orderInfoResponseDto = new OrderInfoResponseDto();
        List<OrderInfoResponseDto> orderInfoResponseDtoList = List.of(orderInfoResponseDto);
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = new PageImpl<>(orderInfoResponseDtoList, pageable, 1);

        Mockito.when(orderHandler.listOrder("pending", 0, 10, request))
                .thenReturn(orderInfoResponseDtoPage);

        ResponseEntity<Page<OrderInfoResponseDto>> response = orderRestController.updateOrderStatus(1L, 0, 10, "pending", request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setReadyStatus(){
        ResponseEntity<Void> response = orderRestController.updateOrderToReady(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setDeliveredStatus(){
        ResponseEntity<Void> response = orderRestController.updateOrderToDelivered(1L, "111111");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
