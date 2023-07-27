package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;

import java.util.List;

public interface IOrderPlatePersistencePort {
    void saveOrderPlate(OrderPlate orderPlate, Order order);

    List<OrderPlate> findAllByOrderId(Long idOrder);
}
