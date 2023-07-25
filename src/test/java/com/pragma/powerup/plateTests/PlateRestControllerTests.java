package com.pragma.powerup.plateTests;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateInfoResponseDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.IPlateHandler;
import com.pragma.powerup.infrastructure.input.rest.PlateRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class PlateRestControllerTests {

    @InjectMocks
    PlateRestController plateRestController;

    @Mock
    IPlateHandler plateHandler;
    @Mock
    HttpServletRequest request;

    @Test
    void savePlate(){
        PlateRequestDto plateRequestDto = new PlateRequestDto();

        ResponseEntity<Void> response = plateRestController.savePlate(plateRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getAllPlates(){
        PlateResponseDto plateResponseDto = new PlateResponseDto();

        Mockito.when(plateHandler.getAllPlates()).thenReturn(List.of(plateResponseDto));

        ResponseEntity<List<PlateResponseDto>> response = plateRestController.getAllPlates();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void updatePlate(){
        PlateRequestDto plateRequestDto = new PlateRequestDto();

        ResponseEntity<Void> response = plateRestController.updatePlate(plateRequestDto, request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void turnOffPlate(){
        Long id = 1L;

        ResponseEntity<Void> response = plateRestController.turnOffOnPlate(id, request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listPlates(){
        Pageable pageable = PageRequest.of(0, 10);
        PlateInfoResponseDto plateInfoResponseDto = new PlateInfoResponseDto();
        List<PlateInfoResponseDto> plateInfoResponseDtos = List.of(plateInfoResponseDto);
        Page<PlateInfoResponseDto> responseDtoPage = new PageImpl<>(plateInfoResponseDtos, pageable, 1);

        Mockito.when(plateHandler.listPlate(1L, 1L, 0, 10)).thenReturn(responseDtoPage);

        ResponseEntity<Page<PlateInfoResponseDto>> response = plateRestController.listPlate(0, 10, "1", 1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
    }
}
