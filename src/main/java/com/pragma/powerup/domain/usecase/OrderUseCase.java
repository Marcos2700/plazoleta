package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    @Override
    public Order saveOrder(Order order) {
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> listOrder(Long idRestaurant, String status, Pageable pageable) {
        return orderPersistencePort.listOrder(idRestaurant, status, pageable);
    }

    @Override
    public Order getOrder(Long id) {
        return orderPersistencePort.getOrder(id);
    }

    @Override
    public void updateStatus(Order order) {


        orderPersistencePort.updateStatus(order);
    }
}
