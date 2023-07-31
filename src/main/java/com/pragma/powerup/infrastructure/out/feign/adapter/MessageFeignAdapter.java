package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.feign.IMessageFeignPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.MessageFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFeignAdapter implements IMessageFeignPersistencePort {

    private final MessageFeignClient messageFeignClient;


    @Override
    public void sendMessage(ClientMessageDto clientMessageDto) {
        messageFeignClient.sendMessage(clientMessageDto);
    }

    @Override
    public void notifyNotIsPossibleToCancel(String phoneNumber) {
        messageFeignClient.notifyNotIsPossibleToCancel(phoneNumber);
    }
}
