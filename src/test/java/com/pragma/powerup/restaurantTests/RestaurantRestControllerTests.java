package com.pragma.powerup.restaurantTests;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.infrastructure.input.rest.RestaurantRestController;
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

import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class RestaurantRestControllerTests {

    @InjectMocks
    RestaurantRestController restaurantRestController;

    @Mock
    IRestaurantHandler restaurantHandler;

    @Test
    void saveRestaurant(){
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();

        ResponseEntity<Void> response = restaurantRestController.saveRestaurant(restaurantRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getAllRestaurants(){
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        Mockito.when(restaurantHandler.getAllRestaurant()).thenReturn(List.of(restaurantResponseDto));

        ResponseEntity<List<RestaurantResponseDto>> response = restaurantRestController.getAllRestaurant();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void listRestaurants(){
        Pageable pageable = PageRequest.of(0, 10);
        RestaurantInfoResponseDto responseDto = new RestaurantInfoResponseDto();
        List<RestaurantInfoResponseDto> dtoList = List.of(responseDto);
        Page<RestaurantInfoResponseDto> pageRestaurantResponse = new PageImpl<>(dtoList, pageable, 1);

        Mockito.when(restaurantHandler.listRestaurant(pageable.getPageNumber(), pageable.getPageSize()))
                .thenReturn(pageRestaurantResponse);

        ResponseEntity<Page<RestaurantInfoResponseDto>> response = restaurantRestController.listRestaurant(pageable.getPageNumber(), pageable.getPageSize());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
    }
}
