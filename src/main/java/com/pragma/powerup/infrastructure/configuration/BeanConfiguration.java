package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.out.feign.TraceabilityFeignClient;
import com.pragma.powerup.infrastructure.out.jpa.adapter.*;
import com.pragma.powerup.infrastructure.out.jpa.mapper.*;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import feign.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    private final IOrderPlateRepository orderPlateRepository;
    private final PinOrderRepository pinOrderRepository;
    private final IPinOrderEntityMapper pinOrderEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort(){
        return new PlateJpaAdapter(plateRepository, plateEntityMapper,  restaurantRepository);
    }

    @Bean
    public IPlateServicePort plateServicePort(){
        return new PlateUseCase(platePersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, restaurantRepository);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort());
    }

    @Bean
    public IOrderPlatePersistencePort orderPlatePersistencePort(){
        return new OrderPlateJpaAdapter(orderPlateRepository, orderPlateEntityMapper, plateRepository);
    }

    @Bean
    public IOrderPlateServicePort orderPlateServicePort(){
        return new OrderPlateUseCase(orderPlatePersistencePort());
    }

    @Bean
    public IPinOrderPersistencePort pinOrderPersistencePort(){
        return new PinOrderJpaAdapter(pinOrderRepository, pinOrderEntityMapper);
    }

    @Bean
    public IPinOrderServicePort pinOrderServicePort(){
        return new PinOrderUseCase(pinOrderPersistencePort());
    }

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }


}