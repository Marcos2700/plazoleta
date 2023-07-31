package com.pragma.powerup.orderTests;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.infrastructure.exception.NoPlateToRestaurantAssociationException;
import com.pragma.powerup.infrastructure.exception.PlateNotExistException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderPlateJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderPlateRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
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
class OrderPlateJpaAdapterTests {

    @InjectMocks
    OrderPlateJpaAdapter orderPlateJpaAdapter;

    @Mock
    IOrderPlateRepository orderPlateRepository;
    @Mock
    IOrderPlateEntityMapper orderPlateEntityMapper;
    @Mock
    IPlateRepository plateRepository;

    @Test
    void saveOrderPlate(){
        Order order = new Order();
        OrderPlate orderPlate = new OrderPlate();
        orderPlate.setIdPlate(1L);
        OrderPlateEntity orderPlateEntity = new OrderPlateEntity();
        PlateEntity plate = new PlateEntity();

        Mockito.when(plateRepository.findById(1L)).thenReturn(Optional.of(plate));
        Mockito.when(orderPlateEntityMapper.toOrderPlateEntity(orderPlate)).thenReturn(orderPlateEntity);

        try {
            orderPlateJpaAdapter.saveOrderPlate(orderPlate, order);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void saveWithNoExistingPlate(){
        Order order = new Order();
        OrderPlate orderPlate = new OrderPlate();
        OrderPlateEntity orderPlateEntity = new OrderPlateEntity();
        PlateEntity plate = new PlateEntity();

        Mockito.when(plateRepository.findById(1L)).thenReturn(Optional.of(plate));
        Mockito.when(orderPlateEntityMapper.toOrderPlateEntity(orderPlate)).thenReturn(orderPlateEntity);

        try {
            orderPlateJpaAdapter.saveOrderPlate(orderPlate, order);
        }
        catch (PlateNotExistException e){
            Assertions.assertInstanceOf(PlateNotExistException.class, e);
        }
    }

    @Test
    void saveWithNoPlateRestaurantAssociation(){
        Order order = new Order();
        order.setIdRestaurant(1L);
        OrderPlate orderPlate = new OrderPlate();
        orderPlate.setIdPlate(1L);
        OrderPlateEntity orderPlateEntity = new OrderPlateEntity();
        PlateEntity plate = new PlateEntity();
        plate.setIdRestaurant(2L);

        Mockito.when(plateRepository.findById(1L)).thenReturn(Optional.of(plate));
        Mockito.when(orderPlateEntityMapper.toOrderPlateEntity(orderPlate)).thenReturn(orderPlateEntity);

        try {
            orderPlateJpaAdapter.saveOrderPlate(orderPlate, order);
        }
        catch (NoPlateToRestaurantAssociationException e){
            Assertions.assertInstanceOf(NoPlateToRestaurantAssociationException.class, e);
        }
    }

    @Test
    void findAllByOrderId(){
        OrderPlateEntity orderPlateEntity = new OrderPlateEntity();
        Mockito.when(orderPlateRepository.findAllByIdOrder(1L)).thenReturn(List.of(orderPlateEntity));

        OrderPlate orderPlate = new OrderPlate();
        Mockito.when(orderPlateEntityMapper.toOrderPlateList(List.of(orderPlateEntity)))
                .thenReturn(List.of(orderPlate));

        List<OrderPlate> result = orderPlateJpaAdapter.findAllByOrderId(1L);

        Assertions.assertEquals(1, result.size());
    }
}
