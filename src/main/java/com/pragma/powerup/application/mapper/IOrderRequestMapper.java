package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {
    @Mapping(target = "idClient", source = "orderRequestDto.idClient")
    @Mapping(target = "date", source = "orderRequestDto.date")
    @Mapping(target = "status", source = "orderRequestDto.status")
    @Mapping(target = "idChef", source = "orderRequestDto.idChef")
    @Mapping(target = "idRestaurant", source = "orderRequestDto.idRestaurant")
    Order toOrder(OrderRequestDto orderRequestDto);

}
