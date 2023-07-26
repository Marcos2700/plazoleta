package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.domain.model.OrderPlate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderPlateMapper {
    OrderPlate toOrderPlate(OrderPlateDto orderPlateDto);
}
