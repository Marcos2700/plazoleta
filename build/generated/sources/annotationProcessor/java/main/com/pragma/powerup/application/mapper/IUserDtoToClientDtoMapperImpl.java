package com.pragma.powerup.application.mapper;

import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T08:49:30-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class IUserDtoToClientDtoMapperImpl implements IUserDtoToClientDtoMapper {

    @Override
    public ClientMessageDto toClientMessageDto(UserDto userDto, String pin) {
        if ( userDto == null && pin == null ) {
            return null;
        }

        ClientMessageDto clientMessageDto = new ClientMessageDto();

        if ( userDto != null ) {
            clientMessageDto.setId( userDto.getId() );
            clientMessageDto.setName( userDto.getName() );
            clientMessageDto.setLastname( userDto.getLastname() );
            clientMessageDto.setIdentityDocument( userDto.getIdentityDocument() );
            clientMessageDto.setPhoneNumber( userDto.getPhoneNumber() );
            clientMessageDto.setDateOfBirth( userDto.getDateOfBirth() );
            clientMessageDto.setEmail( userDto.getEmail() );
            clientMessageDto.setPassword( userDto.getPassword() );
        }
        clientMessageDto.setPin( pin );

        return clientMessageDto;
    }
}
