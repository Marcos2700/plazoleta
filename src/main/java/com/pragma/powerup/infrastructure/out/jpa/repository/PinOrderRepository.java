package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinOrderRepository extends JpaRepository<PinOrderEntity, Long> {
    PinOrderEntity findByIdOrder(Long idOrder);
}
