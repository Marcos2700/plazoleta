package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    default Page<RestaurantInfoResponseDto> toRestaurantResponsePage(Page<Restaurant> restaurantPage){
        List<RestaurantInfoResponseDto> restaurantResponseDtoList = restaurantPage.getContent()
                .stream().map(restaurant -> {
                    RestaurantInfoResponseDto responseDto = new RestaurantInfoResponseDto();
                    responseDto.setName(restaurant.getName());
                    responseDto.setUrlLogo(restaurant.getUrlLogo());
                    return responseDto;
                }).collect(Collectors.toList());
        return new PageImpl<>(restaurantResponseDtoList, restaurantPage.getPageable(), restaurantPage.getSize());
    }
}
