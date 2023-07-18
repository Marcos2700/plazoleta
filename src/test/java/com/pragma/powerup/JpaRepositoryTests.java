package com.pragma.powerup;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryTests {

    @Autowired
    IRestaurantRepository restaurantRepository;

    @Test
    void saveRestaurant(){

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName("Example12");
        restaurant.setAddress("Example xxxxx");
        restaurant.setIdOwner(1L);
        restaurant.setPhoneNumber("+455644848");
        restaurant.setNit("45484946545");
        restaurant.setUrlLogo("Example.image.jpg");

        restaurantRepository.save(restaurant);

        RestaurantEntity restaurantFromDatabase = restaurantRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(restaurantFromDatabase);
        Assertions.assertInstanceOf(RestaurantEntity.class, restaurantFromDatabase);

    }
}
