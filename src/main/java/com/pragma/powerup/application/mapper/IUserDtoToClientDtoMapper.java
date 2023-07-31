package com.pragma.powerup.application.mapper;

import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserDtoToClientDtoMapper {

    @Mapping(target = "pin", source = "pin")
    ClientMessageDto toClientMessageDto(UserDto userDto, String pin);
}
