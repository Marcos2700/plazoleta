package com.pragma.powerup.HU3Tests;

import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
}
