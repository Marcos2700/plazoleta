package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.domain.spi.IPlatePersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.NoPlateToRestaurantAssociationException;
import com.pragma.powerup.infrastructure.exception.PlateAlreadyExistException;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IPlateRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlateJpaAdapter implements IPlatePersistencePort {

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void savePlate(Plate plate) {
        if (plateRepository.findByName(plate.getName()).isPresent()) {
            throw new PlateAlreadyExistException();
        }
        if(restaurantRepository.findById(plate.getIdRestaurant()).isEmpty()){
            throw new NoPlateToRestaurantAssociationException();
        }
        plateRepository.save(plateEntityMapper.toPlateEntity(plate));
    }

    @Override
    public List<Plate> getAllPlate() {
        List<PlateEntity> plateEntityList = plateRepository.findAll();
        if(plateEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return plateEntityMapper.toPlateList(plateEntityList);
    }

    @Override
    public void updatePlate(Plate plate) {
        plateRepository.save(plateEntityMapper.toPlateEntity(plate));
    }

    @Override
    public Plate getPlate(Long id) {
        Optional<PlateEntity> plateEntity = plateRepository.findById(id);
        if (plateEntity.isEmpty()){
            throw new NoDataFoundException();
        }
        return plateEntityMapper.toPlate(plateEntity.get());
    }

    @Override
    public Page<Plate> listPlate(Long idRestaurant, Long idCategory, Pageable pageable) {
        Page<PlateEntity> plateEntityPage;
        if(idCategory == 0L){
            plateEntityPage = plateRepository.findByIdRestaurant(idRestaurant, pageable);
        }
        else {
            plateEntityPage = plateRepository.findByIdRestaurantAndIdCategory(idRestaurant, idCategory, pageable);
        }

        if(plateEntityPage.isEmpty()){
            throw new NoDataFoundException();
        }

        return plateEntityMapper.toPlatepage(plateEntityPage);
    }
}
