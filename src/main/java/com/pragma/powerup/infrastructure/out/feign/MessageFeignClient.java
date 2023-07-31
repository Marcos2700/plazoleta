package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.configuration.BeanConfiguration;
import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Message-microservice", url = "http://localhost:8083", configuration = BeanConfiguration.class)
public interface MessageFeignClient {

    @PostMapping("/message/client")
    void sendMessage(@RequestBody ClientMessageDto clientMessageDto);

    @PostMapping("/message/info/cancel/{phoneNumber}")
    void notifyNotIsPossibleToCancel(@PathVariable String phoneNumber);
}
