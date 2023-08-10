package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.domain.model.Order;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-09T19:28:37-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class IOrderRequestMapperImpl implements IOrderRequestMapper {

    @Override
    public Order toOrder(OrderRequestDto orderRequestDto) {
        if ( orderRequestDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setIdClient( orderRequestDto.getIdClient() );
        order.setDate( orderRequestDto.getDate() );
        order.setStatus( orderRequestDto.getStatus() );
        order.setIdChef( orderRequestDto.getIdChef() );
        order.setIdRestaurant( orderRequestDto.getIdRestaurant() );

        return order;
    }
}
