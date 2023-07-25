package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateInfoResponseDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPlateHandler {

    void savePlate(PlateRequestDto plateRequestDto);

    List<PlateResponseDto> getAllPlates();

    void updatePlate(PlateRequestDto plateResponseDto, HttpServletRequest request);

    PlateResponseDto getPlate(Long id);

    void turnOffOnPlate(Long id, HttpServletRequest request);

    Page<PlateInfoResponseDto> listPlate(Long idRestaurant, Long idCategory, int page, int size);
}
