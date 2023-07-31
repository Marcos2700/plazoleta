package com.pragma.powerup.domain.usecase.feign;

import com.pragma.powerup.domain.api.feign.IUserFeignServicePort;
import com.pragma.powerup.domain.spi.feign.IUserFeignPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignUseCase implements IUserFeignServicePort {

    private final IUserFeignPersistencePort userFeignPersistencePort;

    @Override
    public UserDto getUser(Long id) {
        return userFeignPersistencePort.getUser(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userFeignPersistencePort.getUserByEmail(email);
    }

    @Override
    public OwnerEmployeeRelation getOwnerEmployeeRelation(Long idEmployee) {
        return userFeignPersistencePort.getOwnerEmployeeRelation(idEmployee);
    }
}
