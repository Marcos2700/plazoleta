package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderServicePort {
    Order saveOrder(Order order);

    Page<Order> listOrder(Long idRestaurant, String status, Pageable pageable);
}
