package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;

import java.util.List;

public interface IOrderPlateServicePort {
    void saveOrderPlate(OrderPlate orderPlate, Order order);

    List<OrderPlate> findAllByOrderId(Long idOrder);
}
