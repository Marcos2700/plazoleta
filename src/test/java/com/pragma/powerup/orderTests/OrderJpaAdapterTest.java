package com.pragma.powerup.orderTests;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.exception.OrderInProcessException;
import com.pragma.powerup.infrastructure.exception.RestaurantNotExistException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class OrderJpaAdapterTest {

    @InjectMocks
    OrderJpaAdapter orderJpaAdapter;

    @Mock
    IOrderRepository orderRepository;
    @Mock
    IOrderEntityMapper orderEntityMapper;
    @Mock
    IRestaurantRepository restaurantRepository;

    @Test
    void saveOrder(){
        OrderEntity orderEntity = new OrderEntity();

        Order order = new Order();
        order.setId(1L);
        order.setIdRestaurant(1L);
        order.setIdClient(1L);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName("Example");

        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Mockito.when(orderRepository.findAllByIdClient(1L)).thenReturn(new ArrayList<>());
        Mockito.when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        Mockito.when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        Order resultOrder = orderJpaAdapter.saveOrder(order);

        Assertions.assertEquals(order, resultOrder);

    }

    @Test
    void saveWithNoExistingRestaurant(){
        OrderEntity orderEntity = new OrderEntity();
        List<OrderEntity> pendingOrders = List.of();
        Order order = new Order();
        order.setId(1L);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName("Example");

        Mockito.when(restaurantRepository.findById(order.getId())).thenReturn(Optional.of(restaurant));
        Mockito.when(orderRepository.findAllByIdClient(1L)).thenReturn(pendingOrders);
        Mockito.when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        try{
            Order resultOrder = orderJpaAdapter.saveOrder(order);
        }
        catch (RestaurantNotExistException e){
            Assertions.assertInstanceOf(RestaurantNotExistException.class, e);
        }
    }

    @Test
    void saveWithPendingOrder(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("pending");

        Order order = new Order();
        order.setId(1L);
        order.setIdRestaurant(1L);
        order.setIdClient(1L);

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName("Example");

        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Mockito.when(orderRepository.findAllByIdClient(1L)).thenReturn(List.of(orderEntity));
        Mockito.when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        Mockito.when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        try{
            Order resultOrder = orderJpaAdapter.saveOrder(order);
        }
        catch (OrderInProcessException e){
            Assertions.assertInstanceOf(OrderInProcessException.class, e);
        }
    }
}
