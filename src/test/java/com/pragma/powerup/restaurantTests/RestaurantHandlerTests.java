package com.pragma.powerup.restaurantTests;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.impl.RestaurantHandlerImpl;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.IsNotAOwnerException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.input.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.input.feign.dto.RoleDto;
import com.pragma.powerup.infrastructure.input.feign.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class RestaurantHandlerTests {

    @InjectMocks
    RestaurantHandlerImpl restaurantHandler;

    @Mock
    IRestaurantServicePort restaurantServicePort;
    @Mock
    IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    IRestaurantResponseMapper restaurantResponseMapper;
    @Mock
    UserFeignClient userFeignClient;

    @BeforeEach
    void before(){
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
        Restaurant restaurantMapped = new Restaurant();
        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        Mockito.when(restaurantRequestMapper.toRestaurant(restaurantRequestDto)).thenReturn(restaurantMapped);
        Mockito.when(restaurantResponseMapper.toResponseList(List.of(restaurantMapped))).thenReturn(List.of(restaurantResponseDto));
        Mockito.when(restaurantServicePort.getAllRestaurant()).thenReturn(List.of(restaurantMapped));
    }

    @Test
    void saveRestaurant(){
        UserDto userDto = new UserDto();
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("owner");
        userDto.setRole(roleDto);
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
        restaurantRequestDto.setIdOwner(1L);

        Mockito.when(userFeignClient.getUser(restaurantRequestDto.getIdOwner())).thenReturn(userDto);

        restaurantHandler.saveRestaurant(restaurantRequestDto);

        List<RestaurantResponseDto> restaurantResponseDtoList = restaurantHandler.getAllRestaurant();

        Assertions.assertEquals(1, restaurantResponseDtoList.size());
    }

    @Test
    void saveRestaurantWithNoOwnerRole(){
        UserDto userDto = new UserDto();
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("administrator");
        userDto.setRole(roleDto);
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
        restaurantRequestDto.setIdOwner(1L);

        Mockito.when(userFeignClient.getUser(restaurantRequestDto.getIdOwner())).thenReturn(userDto);

        try{
            restaurantHandler.saveRestaurant(restaurantRequestDto);
        }
        catch (IsNotAOwnerException e){
            Assertions.assertInstanceOf(IsNotAOwnerException.class, e);
        }
    }

    @Test
    void listRestaurants(){
        Pageable pageable = PageRequest.of(0, 10);
        RestaurantInfoResponseDto responseDto = new RestaurantInfoResponseDto();
        List<RestaurantInfoResponseDto> dtoList = List.of(responseDto);
        Page<RestaurantInfoResponseDto> pageRestaurantResponse = new PageImpl<>(dtoList, pageable, 1);

        Restaurant restaurant = new Restaurant();
        List<Restaurant> restaurantList = List.of(restaurant);
        Page<Restaurant> pageRestaurants = new PageImpl<>(restaurantList, pageable, 1);

        Mockito.when(restaurantServicePort.listRestaurant(pageable)).thenReturn(pageRestaurants);
        Mockito.when(restaurantResponseMapper.toRestaurantResponsePage(pageRestaurants)).thenReturn(pageRestaurantResponse);

        Page<RestaurantInfoResponseDto> responseDtoPage = restaurantHandler.listRestaurant(0, 10);

        Assertions.assertFalse(pageRestaurantResponse.isEmpty());
    }
}
