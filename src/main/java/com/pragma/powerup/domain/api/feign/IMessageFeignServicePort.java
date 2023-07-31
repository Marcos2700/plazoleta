package com.pragma.powerup.domain.api.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;

public interface IMessageFeignServicePort {

    void sendMessage(ClientMessageDto clientMessageDto);

    void notifyNotIsPossibleToCancel(String phoneNumber);
}
