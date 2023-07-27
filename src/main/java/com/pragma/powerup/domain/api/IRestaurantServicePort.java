package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantByOwnerId(Long id);

    List<Restaurant> getAllRestaurant();

    Page<Restaurant> listRestaurant(Pageable pageable);
}
