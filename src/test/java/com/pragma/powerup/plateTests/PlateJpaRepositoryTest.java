package com.pragma.powerup.plateTests;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PlateJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
class PlateJpaRepositoryTest {

    @Autowired
    IPlateRepository plateRepository;

    @Test
    void savePlate(){
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setName("Example");
        plateEntity.setDescription("Example description");
        plateEntity.setIdCategory(1L);
        plateEntity.setIdRestaurant(1L);
        plateEntity.setPrice(255);
        plateEntity.setUrlImage("Example");
        plateEntity.setActive(true);

        plateRepository.save(plateEntity);

        PlateEntity plateFromDatabase = plateRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(plateFromDatabase);
        Assertions.assertInstanceOf(PlateEntity.class, plateFromDatabase);

    }

    @Test
    void updatePlate(){
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(1L);
        plateEntity.setName("Example");
        plateEntity.setDescription("Example description");
        plateEntity.setIdCategory(1L);
        plateEntity.setIdRestaurant(1L);
        plateEntity.setPrice(255);
        plateEntity.setUrlImage("Example");
        plateEntity.setActive(true);


        PlateEntity plateFromDatabase = plateRepository.save(plateEntity);

        Assertions.assertEquals(plateEntity.getId(), plateFromDatabase.getId());
        Assertions.assertInstanceOf(PlateEntity.class, plateFromDatabase);
    }

    @Test
    void listPlates(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<PlateEntity> plateEntityPage = plateRepository.findByIdRestaurantAndIdCategory(1L, 1L, pageable);

        Assertions.assertFalse(plateEntityPage.isEmpty());
    }
}
