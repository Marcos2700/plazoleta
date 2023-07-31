package com.pragma.powerup.domain.usecase.feign;

import com.pragma.powerup.domain.api.feign.IMessageFeignServicePort;
import com.pragma.powerup.domain.spi.feign.IMessageFeignPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFeignUseCase implements IMessageFeignServicePort {
    private final IMessageFeignPersistencePort messageFeignPersistencePort;

    @Override
    public void sendMessage(ClientMessageDto clientMessageDto) {
        messageFeignPersistencePort.sendMessage(clientMessageDto);
    }

    @Override
    public void notifyNotIsPossibleToCancel(String phoneNumber) {
        messageFeignPersistencePort.notifyNotIsPossibleToCancel(phoneNumber);
    }
}
