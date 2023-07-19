package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.IPlateHandler;
import com.pragma.powerup.application.mapper.ICategoryDtoMapper;
import com.pragma.powerup.application.mapper.IPlateRequestMapper;
import com.pragma.powerup.application.mapper.IPlateResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IPlateServicePort;
import com.pragma.powerup.domain.model.Plate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlateHandlerImpl implements IPlateHandler {

    private final IPlateServicePort plateServicePort;
    private final ICategoryServicePort categoryServicePort;
    private final IPlateRequestMapper plateRequestMapper;
    private final IPlateResponseMapper plateResponseMapper;
    private final ICategoryDtoMapper categoryDtoMapper;

    @Override
    public void savePlate(PlateRequestDto plateRequestDto) {
        plateRequestDto.setActive(true);
        Plate plate = plateRequestMapper.toPlate(plateRequestDto);
        plateServicePort.savePlate(plate);
    }

    @Override
    public List<PlateResponseDto> getAllPlates() {
        return plateResponseMapper.toResponseList(plateServicePort.getAllPlates(), categoryServicePort.getAllCategory());
    }
}
