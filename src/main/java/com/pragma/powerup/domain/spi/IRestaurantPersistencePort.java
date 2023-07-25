package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurant();

    Page<Restaurant> listRestaurant(Pageable pageable);
}
