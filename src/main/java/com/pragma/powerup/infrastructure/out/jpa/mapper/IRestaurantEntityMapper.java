package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {

    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toRestaurant(RestaurantEntity restaurantEntity);

    List<Restaurant> toRestaurantList(List<RestaurantEntity> restaurantEntityList);

    default Page<Restaurant> toRestaurantPage(Page<RestaurantEntity> restaurantEntityPage){
        List<Restaurant> restaurantList = restaurantEntityPage.getContent()
                .stream()
                .map(this::toRestaurant)
                .collect(Collectors.toList());
        return new PageImpl<>(restaurantList, restaurantEntityPage.getPageable(), restaurantEntityPage.getSize());
    }
}
