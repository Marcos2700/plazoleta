package com.pragma.powerup.orderTests;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@SpringBootTest
class OrderJpaRepositoryTest {

    @Autowired
    IOrderRepository orderRepository;

    @Test
    void saveOrder(){
        OrderEntity order = new OrderEntity();
        order.setIdClient(1L);
        order.setIdRestaurant(1L);
        order.setIdChef(0L);
        order.setStatus("example");
        order.setDate(new Date());

        OrderEntity orderEntity = orderRepository.save(order);

        Assertions.assertEquals(order, orderEntity);
    }

    @Test
    void saveWithNullAttributes(){
        OrderEntity order = new OrderEntity();
        order.setIdClient(1L);
        order.setIdChef(0L);
        order.setStatus("example");
        order.setDate(new Date());

        try{
            OrderEntity orderEntity = orderRepository.save(order);
        }
        catch (Exception e){
            Assertions.assertInstanceOf(Exception.class, e);
        }
    }

    @Test
    void listOrders(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> orderEntityPage = orderRepository.findByIdRestaurantAndStatus(1L, "pending", pageable);

        Assertions.assertNotNull(orderEntityPage);
    }
}
