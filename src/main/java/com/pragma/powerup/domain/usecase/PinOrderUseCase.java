package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IPinOrderServicePort;
import com.pragma.powerup.domain.model.PinOrder;
import com.pragma.powerup.domain.spi.IPinOrderPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinOrderUseCase implements IPinOrderServicePort {
    private final IPinOrderPersistencePort pinOrderPersistencePort;

    @Override
    public void savePinOrder(PinOrder pinOrder) {
        pinOrderPersistencePort.savePinOrder(pinOrder);
    }

    @Override
    public PinOrder getByOrder(Long idOrder) {
        return pinOrderPersistencePort.getByOrder(idOrder);
    }

    @Override
    public void deletePinOrder(Long idPinOrder) {
        pinOrderPersistencePort.deletePinOrder(idPinOrder);
    }
}
