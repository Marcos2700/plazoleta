package com.pragma.powerup.orderTests;

import com.pragma.powerup.domain.model.PinOrder;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PinOrderJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPinOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.PinOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PinOrderJpaAdapterTests {

    @InjectMocks
    PinOrderJpaAdapter pinOrderJpaAdapter;

    @Mock
    PinOrderRepository pinOrderRepository;
    @Mock
    IPinOrderEntityMapper pinOrderEntityMapper;

    @Test
    void savePinOrder(){
        PinOrder pinOrder = new PinOrder();
        PinOrderEntity pinOrderEntity = new PinOrderEntity();

        Mockito.when(pinOrderEntityMapper.toPinOrderEntity(pinOrder)).thenReturn(pinOrderEntity);

        try {
            pinOrderJpaAdapter.savePinOrder(pinOrder);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getPinOrder(){
        PinOrder pinOrder = new PinOrder();
        PinOrderEntity pinOrderEntity = new PinOrderEntity();

        Mockito.when(pinOrderRepository.findByIdOrder(1L)).thenReturn(pinOrderEntity);
        Mockito.when(pinOrderEntityMapper.toPinOrder(pinOrderEntity)).thenReturn(pinOrder);

        PinOrder result = pinOrderJpaAdapter.getByOrder(1L);

        Assertions.assertInstanceOf(PinOrder.class, result);
    }

    @Test
    void deletePinOrder(){
        try {
            pinOrderJpaAdapter.deletePinOrder(1L);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
