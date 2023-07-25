package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    List<RestaurantResponseDto> getAllRestaurant();

    Page<RestaurantInfoResponseDto> listRestaurant(int page, int size);
}
