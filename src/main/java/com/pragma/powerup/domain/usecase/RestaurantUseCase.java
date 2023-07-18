package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.exception.NitNoNumericException;
import com.pragma.powerup.domain.exception.NumericRestaurantNameException;
import com.pragma.powerup.domain.exception.PhoneNumberNoNumericException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if(!isNumeric(restaurant.getPhoneNumber().substring(1))){
            throw new PhoneNumberNoNumericException();
        }
        if(!isNumeric(restaurant.getNit())){
            throw new NitNoNumericException();
        }
        if(isNumeric(restaurant.getName())){
            throw new NumericRestaurantNameException();
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantPersistencePort.getAllRestaurant();
    }

    private static boolean isNumeric(String number){
        try {
            Long.parseLong(number);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
