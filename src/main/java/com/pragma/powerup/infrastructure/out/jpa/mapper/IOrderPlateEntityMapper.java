package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderPlateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderPlateEntityMapper {

    OrderPlateEntity toOrderPlateEntity(OrderPlate orderPlate);

    OrderPlate toOrderPlate(OrderPlateEntity orderPlateEntity);

    default List<OrderPlate> toOrderPlateList(List<OrderPlateEntity> orderPlateEntityList){
        return orderPlateEntityList.stream()
                .map(this::toOrderPlate)
                .collect(Collectors.toList());
    }
}