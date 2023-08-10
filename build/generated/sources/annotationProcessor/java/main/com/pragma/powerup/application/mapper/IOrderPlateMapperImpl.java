package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.domain.model.OrderPlate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T12:24:11-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class IOrderPlateMapperImpl implements IOrderPlateMapper {

    @Override
    public OrderPlate toOrderPlate(OrderPlateDto orderPlateDto) {
        if ( orderPlateDto == null ) {
            return null;
        }

        OrderPlate orderPlate = new OrderPlate();

        orderPlate.setIdOrder( orderPlateDto.getIdOrder() );
        orderPlate.setIdPlate( orderPlateDto.getIdPlate() );
        orderPlate.setQuantity( orderPlateDto.getQuantity() );

        return orderPlate;
    }
}
