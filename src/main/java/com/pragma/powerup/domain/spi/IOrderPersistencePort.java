package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);

    Page<Order> listOrder(Long idRestaurant, String status, Pageable pageable);

    Order getOrder(Long id);

    void updateStatus(Order order);
}
