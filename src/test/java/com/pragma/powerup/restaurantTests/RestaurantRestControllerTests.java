package com.pragma.powerup.restaurantTests;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.infrastructure.input.rest.RestaurantRestController;
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
}
