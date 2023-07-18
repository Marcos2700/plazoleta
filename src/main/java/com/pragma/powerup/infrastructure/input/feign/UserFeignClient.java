package com.pragma.powerup.infrastructure.input.feign;

import com.pragma.powerup.infrastructure.configuration.BeanConfiguration;
import com.pragma.powerup.infrastructure.input.feign.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Users-microservice", url = "http://localhost:8081", configuration = BeanConfiguration.class)
public interface UserFeignClient {

    @GetMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDto getUser(@PathVariable(name = "id") Long id);
}