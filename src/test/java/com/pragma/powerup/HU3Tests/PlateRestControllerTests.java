package com.pragma.powerup.HU3Tests;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.IPlateHandler;
import com.pragma.powerup.infrastructure.input.rest.PlateRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class PlateRestControllerTests {

    @InjectMocks
    PlateRestController plateRestController;

    @Mock
    IPlateHandler plateHandler;

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
}
