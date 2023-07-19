package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateEntityMapper {

    PlateEntity toPlateEntity(Plate plate);

    Plate toPlate(PlateEntity plateEntity);

    List<Plate> toPlateList(List<PlateEntity> plateEntityList);
}
