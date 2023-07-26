package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderPlateMapper;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderPlateServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderPlateServicePort orderPlateServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderPlateMapper orderPlateMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        order.setStatus(OrderStatus.PENDING.getStatus());
        Order orderSaved = orderServicePort.saveOrder(order);

        List<OrderPlateDto> orderPlateDtoList = orderRequestDto.getOrders();

        orderPlateDtoList.forEach(orderPlateDto -> {
                    orderPlateDto.setIdOrder(orderSaved.getId());
                    OrderPlate orderPlate = orderPlateMapper.toOrderPlate(orderPlateDto);
                    orderPlateServicePort.saveOrderPlate(orderPlate, order);
                });
    }
}
