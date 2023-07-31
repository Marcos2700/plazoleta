package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.feign.IUserFeignServicePort;
import com.pragma.powerup.domain.exception.IsNotAOwnerException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandlerImpl implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IUserFeignServicePort userFeignClient;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        UserDto user = userFeignClient.getUser(restaurantRequestDto.getIdOwner());
        if(!user.getRole().getRoleName().equals("owner")){
            throw new IsNotAOwnerException();
        }
        Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurant() {
        return restaurantResponseMapper.toResponseList(restaurantServicePort.getAllRestaurant());
    }

    @Override
    public Page<RestaurantInfoResponseDto> listRestaurant(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return restaurantResponseMapper.toRestaurantResponsePage(restaurantServicePort.listRestaurant(pageable));
    }
}
