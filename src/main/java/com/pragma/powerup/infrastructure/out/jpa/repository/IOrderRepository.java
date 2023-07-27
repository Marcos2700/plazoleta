package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByIdClient(Long idClient);

    Page<OrderEntity> findAllByIdRestaurant(Long idRestaurant, Pageable pageable);

    Page<OrderEntity> findByIdRestaurantAndStatus(Long idRestaurant, String status, Pageable pageable);
}
