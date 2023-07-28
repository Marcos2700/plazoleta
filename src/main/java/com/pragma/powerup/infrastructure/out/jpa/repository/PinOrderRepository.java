package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinOrderRepository extends JpaRepository<PinOrder, Long> {
    PinOrder findByIdOrder(Long idOrder);
}
