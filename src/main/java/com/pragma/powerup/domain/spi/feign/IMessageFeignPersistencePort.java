package com.pragma.powerup.domain.spi.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;

public interface IMessageFeignPersistencePort {
    void sendMessage(ClientMessageDto clientMessageDto);

    void notifyNotIsPossibleToCancel(String phoneNumber);
}
