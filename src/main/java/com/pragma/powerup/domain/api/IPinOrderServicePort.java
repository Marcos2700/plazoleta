package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.PinOrder;

public interface IPinOrderServicePort {
    void savePinOrder(PinOrder pinOrder);
    PinOrder getByOrder(Long idOrder);
    void deletePinOrder(Long idPinOrder);
}