package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderPlateServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.domain.spi.IOrderPlatePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderPlateUseCase implements IOrderPlateServicePort {

    private final IOrderPlatePersistencePort orderPlatePersistencePort;

    @Override
    public void saveOrderPlate(OrderPlate orderPlate, Order order) {
        orderPlatePersistencePort.saveOrderPlate(orderPlate, order);
    }
}
