package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.IPlateHandler;
import com.pragma.powerup.application.mapper.ICategoryDtoMapper;
import com.pragma.powerup.application.mapper.IPlateRequestMapper;
import com.pragma.powerup.application.mapper.IPlateResponseMapper;
import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.api.IPlateServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.exception.NoOwnerPlateAssociationException;
import com.pragma.powerup.infrastructure.input.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final IRestaurantServicePort restaurantServicePort;
    private final UserFeignClient userFeignClient;

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

    @Override
    public void updatePlate(PlateRequestDto plateRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ","");
        TokenUtils tokenUtils = new TokenUtils();
        String email = tokenUtils.getEmail(token);

        Restaurant restaurant = restaurantServicePort.getAllRestaurant().stream().filter(restaurantById -> restaurantById.getId().equals(plateRequestDto.getIdRestaurant())).findFirst().orElse(new Restaurant());

        Long idRestaurantOwner = restaurant.getIdOwner();

        if(!email.equals(userFeignClient.getUser(idRestaurantOwner).getEmail())){
            throw new NoOwnerPlateAssociationException();
        }

        Plate plate = plateServicePort.getPlate(plateRequestDto.getId());

        plate.setDescription(plateRequestDto.getDescription());
        plate.setPrice(plateRequestDto.getPrice());

        plateServicePort.updatePlate(plate);
    }

    @Override
    public PlateResponseDto getPlate(Long id) {
        Plate plate = plateServicePort.getPlate(id);
        return plateResponseMapper.toResponse(plate, categoryDtoMapper.toCategoryDto(categoryServicePort.getCategory(plate.getId())));
    }

}
