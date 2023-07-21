package com.pragma.powerup.plateTests;

import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
