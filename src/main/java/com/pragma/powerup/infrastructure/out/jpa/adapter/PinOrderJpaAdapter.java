package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.PinOrder;
import com.pragma.powerup.domain.spi.IPinOrderPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPinOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.PinOrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinOrderJpaAdapter implements IPinOrderPersistencePort {
    private final PinOrderRepository pinOrderRepository;
    private final IPinOrderEntityMapper pinOrderEntityMapper;

    @Override
    public void savePinOrder(PinOrder pinOrder) {
        pinOrderRepository.save(pinOrderEntityMapper.toPinOrderEntity(pinOrder));
    }

    @Override
    public PinOrder getByOrder(Long idOrder) {
        return pinOrderEntityMapper.toPinOrder(pinOrderRepository.findByIdOrder(idOrder));
    }

    @Override
    public void deletePinOrder(Long idPinOrder) {
        pinOrderRepository.deleteById(idPinOrder);
    }
}
