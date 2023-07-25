package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Plate;
import com.pragma.powerup.infrastructure.out.jpa.entity.PlateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateEntityMapper {

    PlateEntity toPlateEntity(Plate plate);

    Plate toPlate(PlateEntity plateEntity);

    List<Plate> toPlateList(List<PlateEntity> plateEntityList);

    default Page<Plate> toPlatepage(Page<PlateEntity> plateEntityPage){
        List<Plate> plateList = plateEntityPage.getContent().stream()
                .map(this::toPlate)
                .collect(Collectors.toList());
        return new PageImpl<>(plateList, plateEntityPage.getPageable(), plateEntityPage.getSize());
    }
}
