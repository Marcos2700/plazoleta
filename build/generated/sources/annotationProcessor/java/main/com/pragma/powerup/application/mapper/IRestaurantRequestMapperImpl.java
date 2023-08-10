package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.domain.model.Restaurant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T12:24:11-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class IRestaurantRequestMapperImpl implements IRestaurantRequestMapper {

    @Override
    public Restaurant toRestaurant(RestaurantRequestDto restaurantRequestDto) {
        if ( restaurantRequestDto == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName( restaurantRequestDto.getName() );
        restaurant.setAddress( restaurantRequestDto.getAddress() );
        restaurant.setIdOwner( restaurantRequestDto.getIdOwner() );
        restaurant.setPhoneNumber( restaurantRequestDto.getPhoneNumber() );
        restaurant.setUrlLogo( restaurantRequestDto.getUrlLogo() );
        restaurant.setNit( restaurantRequestDto.getNit() );

        return restaurant;
    }
}
