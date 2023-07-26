package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    OrderEntity toOrderEntity(Order order);

    Order toOrder(OrderEntity orderEntity);
}
