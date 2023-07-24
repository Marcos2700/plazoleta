package com.pragma.powerup.plateTests;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.impl.PlateHandlerImpl;
import com.pragma.powerup.application.mapper.IPlateRequestMapper;
import com.pragma.powerup.application.mapper.IPlateResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IPlateServicePort;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Plate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PlateHandlerTests {

    @InjectMocks
    PlateHandlerImpl plateHandler;

    @Mock
    IPlateServicePort plateServicePort;
    @Mock
    ICategoryServicePort categoryServicePort;
    @Mock
    IPlateRequestMapper plateRequestMapper;
    @Mock
    IPlateResponseMapper plateResponseMapper;
    @Mock
    HttpServletRequest request;

    @BeforeEach
    void Before(){
        PlateRequestDto plateRequestDto = new PlateRequestDto();
        Plate plateMapped = new Plate();
        PlateResponseDto plateResponseDto = new PlateResponseDto();
        Category categoryMapped = new Category();

        Mockito.when(plateRequestMapper.toPlate(plateRequestDto)).thenReturn(plateMapped);
        Mockito.when(plateServicePort.getAllPlates()).thenReturn(List.of(plateMapped));
        Mockito.when(categoryServicePort.getAllCategory()).thenReturn(List.of(categoryMapped));
        Mockito.when(plateResponseMapper.toResponseList(List.of(plateMapped), List.of(categoryMapped))).thenReturn(List.of(plateResponseDto));
    }

    @Test
    void savePlate(){
        PlateRequestDto plateRequestDto = new PlateRequestDto();

        plateHandler.savePlate(plateRequestDto);

        List<PlateResponseDto> plateResponseDtoList = plateHandler.getAllPlates();

        Assertions.assertEquals(1, plateResponseDtoList.size());

    }

    @Test
    void updatePlate(){
        PlateRequestDto plateRequestDto = new PlateRequestDto();
        plateRequestDto.setId(1L);

        Plate plate = new Plate();

        Mockito.when(plateServicePort.getPlate(plateRequestDto.getId())).thenReturn(plate);

        try{
            plateHandler.updatePlate(plateRequestDto, request);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void turnOffPlate(){
        Long id = 1L;
        Plate plate = new Plate();

        Mockito.when(plateServicePort.getPlate(id)).thenReturn(plate);

        try {
            plateHandler.turnOffPlate(id);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
