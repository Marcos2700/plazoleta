package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderPlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderPlateRepository extends JpaRepository<OrderPlateEntity, Long> {

    List<OrderPlateEntity> findAllByIdOrder(Long idOrder);
}
