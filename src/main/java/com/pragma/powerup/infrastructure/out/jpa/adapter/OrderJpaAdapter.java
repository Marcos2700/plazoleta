package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.exception.OrderInProcessException;
import com.pragma.powerup.infrastructure.exception.RestaurantNotExistException;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public Order saveOrder(Order order) {
        RestaurantEntity restaurant = restaurantRepository.findById(order.getIdRestaurant()).orElse(null);
        if(restaurant == null){
            throw new RestaurantNotExistException();
        }
        List<OrderEntity> orderEntityList = orderRepository.findAllByIdClient(order.getIdClient());
        List<OrderEntity> inProcessOrders = orderEntityList.stream()
                        .filter(orderEntity -> Objects.equals(orderEntity.getStatus(), OrderStatus.IN_PREPARATION.getStatus()) ||
                                Objects.equals(orderEntity.getStatus(), OrderStatus.PENDING.getStatus()) ||
                                Objects.equals(orderEntity.getStatus(), OrderStatus.READY.getStatus()))
                                .collect(Collectors.toList());
        if(!inProcessOrders.isEmpty()){
            throw new OrderInProcessException();
        }
        return orderEntityMapper.toOrder(orderRepository.save(orderEntityMapper.toOrderEntity(order)));
    }
}
