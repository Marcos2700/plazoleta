package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.domain.spi.IOrderPlatePersistencePort;
import com.pragma.powerup.infrastructure.exception.NoPlateToRestaurantAssociationException;
import com.pragma.powerup.infrastructure.exception.PlateNotExistException;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderPlateRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class OrderPlateJpaAdapter implements IOrderPlatePersistencePort {

    private final IOrderPlateRepository orderPlateRepository;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    private final IPlateRepository plateRepository;

    @Override
    public void saveOrderPlate(OrderPlate orderPlate, Order order) {
        PlateEntity plate = plateRepository.findById(orderPlate.getIdPlate()).orElse(null);
        if(plate == null){
            throw new PlateNotExistException();
        }
        if(!Objects.equals(plate.getIdRestaurant(), order.getIdRestaurant())){
            throw new NoPlateToRestaurantAssociationException();
        }
        orderPlateRepository.save(orderPlateEntityMapper.toOrderPlateEntity(orderPlate));
    }

    @Override
    public List<OrderPlate> findAllByOrderId(Long idOrder) {
        return orderPlateEntityMapper.toOrderPlateList(orderPlateRepository.findAllByIdOrder(idOrder));
    }
}
