package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.feign.IUserFeignPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserFeignPersistencePort {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDto getUser(Long id) {
        return userFeignClient.getUser(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userFeignClient.getUserByEmail(email);
    }

    @Override
    public OwnerEmployeeRelation getOwnerEmployeeRelation(Long idEmployee) {
        return userFeignClient.getOwnerEmployeeRelation(idEmployee);
    }
}
