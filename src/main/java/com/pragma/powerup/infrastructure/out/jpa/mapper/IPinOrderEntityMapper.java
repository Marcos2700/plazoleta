package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.PinOrder;
import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPinOrderEntityMapper {
    PinOrderEntity toPinOrderEntity(PinOrder pinOrder);

    PinOrder toPinOrder(PinOrderEntity pinOrderEntity);
}
