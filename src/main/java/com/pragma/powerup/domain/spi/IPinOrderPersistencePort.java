package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.PinOrder;

public interface IPinOrderPersistencePort {
    void savePinOrder(PinOrder pinOrder);
    PinOrder getByOrder(Long idOrder);

    void deletePinOrder(Long idPinOrder);
}
