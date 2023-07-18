package com.pragma.powerup;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.RestaurantAlreadyExistException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class RestaurantJpaAdapterTests {

    @InjectMocks
    RestaurantJpaAdapter restaurantJpaAdapter;

    @Mock
    IRestaurantRepository restaurantRepository;
    @Mock
    IRestaurantEntityMapper restaurantEntityMapper;

    @Test
    void saveRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setNit("11111111111");

        RestaurantEntity restaurantEntity = new RestaurantEntity();

        Mockito.when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(restaurantEntity);
        Mockito.when(restaurantRepository.findByNit(restaurant.getNit())).thenReturn(Optional.empty());
        Mockito.when(restaurantEntityMapper.toRestaurantList(List.of(restaurantEntity))).thenReturn(List.of(restaurant));
        Mockito.when(restaurantRepository.findAll()).thenReturn(List.of(restaurantEntity));

        restaurantJpaAdapter.saveRestaurant(restaurant);

        List<Restaurant> restaurantList = restaurantJpaAdapter.getAllRestaurant();

        Assertions.assertEquals(1, restaurantList.size());
    }

    @Test
    void saveExistingRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setNit("11111111111");

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setNit("11111111111");

        Mockito.when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(restaurantEntity);
        Mockito.when(restaurantRepository.findByNit(restaurant.getNit())).thenReturn(Optional.of(restaurantEntity));

        try{
            restaurantJpaAdapter.saveRestaurant(restaurant);
        }
        catch (RestaurantAlreadyExistException e){
            Assertions.assertInstanceOf(RestaurantAlreadyExistException.class, e);
        }

    }

    @Test
    void getEmptyList(){
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        Mockito.when(restaurantRepository.findAll()).thenReturn(List.of());
        Mockito.when(restaurantEntityMapper.toRestaurantList(List.of(restaurantEntity))).thenReturn(List.of());

        try {
            restaurantJpaAdapter.getAllRestaurant();
        }
        catch (NoDataFoundException e){
            Assertions.assertInstanceOf(NoDataFoundException.class, e);
        }
    }
}
