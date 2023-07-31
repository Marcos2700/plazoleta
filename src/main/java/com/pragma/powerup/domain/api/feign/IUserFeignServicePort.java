package com.pragma.powerup.domain.api.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;

public interface IUserFeignServicePort {
    UserDto getUser(Long id);

    UserDto getUserByEmail(String email);

    OwnerEmployeeRelation getOwnerEmployeeRelation(Long idEmployee);
}
