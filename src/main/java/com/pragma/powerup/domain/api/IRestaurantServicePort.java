package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {

    public void saveRestaurant(Restaurant restaurant);

    public List<Restaurant> getAllRestaurant();
}
