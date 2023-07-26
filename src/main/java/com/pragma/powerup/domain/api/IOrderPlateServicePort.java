package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;

public interface IOrderPlateServicePort {
    void saveOrderPlate(OrderPlate orderPlate, Order order);
}
