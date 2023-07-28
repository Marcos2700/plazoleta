package com.pragma.powerup.application.handler;


import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequestDto);

    Page<OrderInfoResponseDto> updateOrderStatus(Long idOrder, String status, int page, int size, HttpServletRequest request);

    Page<OrderInfoResponseDto> listOrder(String status, int page, int size, HttpServletRequest request);

    void setReadyStatus(Long idOrder);
}
