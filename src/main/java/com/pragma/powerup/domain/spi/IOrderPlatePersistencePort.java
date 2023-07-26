package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;

public interface IOrderPlatePersistencePort {
    void saveOrderPlate(OrderPlate orderPlate, Order order);
}
