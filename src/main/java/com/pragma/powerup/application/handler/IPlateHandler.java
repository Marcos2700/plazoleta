package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;

import java.util.List;

public interface IPlateHandler {

    void savePlate(PlateRequestDto plateRequestDto);

    List<PlateResponseDto> getAllPlates();

    void updatePlate(PlateRequestDto plateResponseDto);

    PlateResponseDto getPlate(Long id);
}
