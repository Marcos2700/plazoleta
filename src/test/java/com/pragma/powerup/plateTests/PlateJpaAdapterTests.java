package com.pragma.powerup.plateTests;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.NoPlateToRestaurantAssociationException;
import com.pragma.powerup.infrastructure.exception.PlateAlreadyExistException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PlateJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PlateJpaAdapterTests {

    @InjectMocks
    PlateJpaAdapter plateJpaAdapter;

    @Mock
    IPlateRepository plateRepository;
    @Mock
    IPlateEntityMapper plateEntityMapper;
    @Mock
    IRestaurantRepository restaurantRepository;

    @Test
    void savePlate(){
        Plate plate = new Plate();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        PlateEntity plateEntity = new PlateEntity();

        Mockito.when(plateRepository.findByName(plate.getName())).thenReturn(Optional.empty());
        Mockito.when(restaurantRepository.findById(plate.getId())).thenReturn(Optional.of(restaurantEntity));
        Mockito.when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);
        Mockito.when(plateEntityMapper.toPlateList(List.of(plateEntity))).thenReturn(List.of(plate));
        Mockito.when(plateRepository.findAll()).thenReturn(List.of(plateEntity));

        plateJpaAdapter.savePlate(plate);

        List<Plate> plateList = plateJpaAdapter.getAllPlate();

        Assertions.assertEquals(1, plateList.size());

    }

    @Test
    void saveExistingPlate(){
        Plate plate = new Plate();
        plate.setName("Example");
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setName("Example");

        Mockito.when(plateRepository.findByName(plate.getName())).thenReturn(Optional.of(plateEntity));
        Mockito.when(restaurantRepository.findById(plate.getId())).thenReturn(Optional.of(restaurantEntity));
        Mockito.when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);

        try{
            plateJpaAdapter.savePlate(plate);
        }
        catch (PlateAlreadyExistException e){
            Assertions.assertInstanceOf(PlateAlreadyExistException.class, e);
        }

    }

    @Test
    void saveNoRestaurantAssociatedPlate(){
        Plate plate = new Plate();
        PlateEntity plateEntity = new PlateEntity();

        Mockito.when(plateRepository.findByName(plate.getName())).thenReturn(Optional.empty());
        Mockito.when(restaurantRepository.findById(plate.getId())).thenReturn(Optional.empty());
        Mockito.when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);

        try{
            plateJpaAdapter.savePlate(plate);
        }
        catch (NoPlateToRestaurantAssociationException e){
            Assertions.assertInstanceOf(NoPlateToRestaurantAssociationException.class, e);
        }

    }

    @Test
    void updatePlate(){
        Plate plate = new Plate();
        PlateEntity plateEntity = new PlateEntity();

        Mockito.when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);

        try{
            plateJpaAdapter.updatePlate(plate);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void listPlates(){
        Pageable pageable = PageRequest.of(0, 10);
        PlateEntity plateEntity = new PlateEntity();
        List<PlateEntity> plateEntityList = List.of(plateEntity);
        Page<PlateEntity> plateEntityPage = new PageImpl<>(plateEntityList, pageable, 1);

        Mockito.when(plateRepository.findByIdRestaurantAndIdCategory(1L, 1L, pageable))
                .thenReturn(plateEntityPage);

        Plate plate = new Plate();
        List<Plate> plateList = List.of(plate);
        Page<Plate> platePage = new PageImpl<>(plateList, pageable, 1);

        Mockito.when(plateEntityMapper.toPlatepage(plateEntityPage)).thenReturn(platePage);

        Page<Plate> returnedPlatePage = plateJpaAdapter.listPlate(1L, 1L, pageable);

        Assertions.assertInstanceOf(Page.class, returnedPlatePage);
        Assertions.assertFalse(returnedPlatePage.isEmpty());
    }

    @Test
    void returningEmptyPage(){
        Pageable pageable = PageRequest.of(0, 10);
        List<PlateEntity> plateEntityList = List.of();
        Page<PlateEntity> plateEntityPage = new PageImpl<>(plateEntityList, pageable, 1);

        Mockito.when(plateRepository.findByIdRestaurantAndIdCategory(1L, 1L, pageable))
                .thenReturn(plateEntityPage);

        try{
            Page<Plate> page = plateJpaAdapter.listPlate(1L, 1L, pageable);
        }
        catch (NoDataFoundException e){
            Assertions.assertInstanceOf(NoDataFoundException.class, e);
        }
    }
}
