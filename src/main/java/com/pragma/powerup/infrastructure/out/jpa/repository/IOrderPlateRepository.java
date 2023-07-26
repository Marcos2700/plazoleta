package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderPlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderPlateRepository extends JpaRepository<OrderPlateEntity, Long> {
}
