package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {

    default List<RestaurantResponseDto> toResponseList(List<Restaurant> restaurantList){
        return restaurantList.stream()
                .map(restaurant -> {
                    RestaurantResponseDto restaurantResponse = new RestaurantResponseDto();
                    restaurantResponse.setName(restaurant.getName());
                    restaurantResponse.setAddress(restaurant.getAddress());
                    restaurantResponse.setIdOwner(restaurant.getIdOwner());
                    restaurantResponse.setPhoneNumber(restaurant.getPhoneNumber());
                    restaurantResponse.setUrlLogo(restaurant.getUrlLogo());
                    restaurantResponse.setNit(restaurant.getNit());
                    return restaurantResponse;
                }).collect(Collectors.toList());
    }
}
