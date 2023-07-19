package com.pragma.powerup.HU2Tests;

import com.pragma.powerup.domain.exception.NitNoNumericException;
import com.pragma.powerup.domain.exception.NumericRestaurantNameException;
import com.pragma.powerup.domain.exception.PhoneNumberNoNumericException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class RestaurantUseCaseTests {

    @InjectMocks
    RestaurantUseCase restaurantUseCase;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Test
    void saveRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Example1123");
        restaurant.setPhoneNumber("+45498795425");
        restaurant.setNit("41158879996");

        Mockito.when(restaurantPersistencePort.getAllRestaurant()).thenReturn(List.of(restaurant));

        restaurantUseCase.saveRestaurant(restaurant);

        Assertions.assertNotNull(restaurantUseCase.getAllRestaurant());

    }

    @Test
    void saveRestaurantWithOnlyNumericName(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("4545451123");
        restaurant.setPhoneNumber("+45498795425");
        restaurant.setNit("41158879996");

        try{
            restaurantUseCase.saveRestaurant(restaurant);
        }
        catch (NumericRestaurantNameException e){
            Assertions.assertInstanceOf(NumericRestaurantNameException.class, e);
        }
    }

    @Test
    void saveRestaurantWithNoNumericPhoneNumber(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Example12135");
        restaurant.setPhoneNumber("+454987f5425");
        restaurant.setNit("41158879996");

        try {
            restaurantUseCase.saveRestaurant(restaurant);
        }
        catch (PhoneNumberNoNumericException e){
            Assertions.assertInstanceOf(PhoneNumberNoNumericException.class, e);
        }
    }

    @Test
    void saveRestaurantWithNoNumericNit(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("4545451123");
        restaurant.setPhoneNumber("+45498795425");
        restaurant.setNit("41aaaa9996");

        try{
            restaurantUseCase.saveRestaurant(restaurant);
        }
        catch (NitNoNumericException e){
            Assertions.assertInstanceOf(NitNoNumericException.class, e);
        }
    }
}
