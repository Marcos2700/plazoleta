package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPlateHandler {

    void savePlate(PlateRequestDto plateRequestDto);

    List<PlateResponseDto> getAllPlates();

    void updatePlate(PlateRequestDto plateResponseDto, HttpServletRequest request);

    PlateResponseDto getPlate(Long id);

    void turnOffOnPlate(Long id, HttpServletRequest request);
}
