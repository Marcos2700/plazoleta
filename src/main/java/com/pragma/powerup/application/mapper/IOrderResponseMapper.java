package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    default Page<OrderInfoResponseDto> toReponsePage(Page<Order> orderPage, List<OrderPlate> orderPlateList){
        List<OrderInfoResponseDto> orderInfoResponseDtoList = orderPage.getContent().stream()
                .map(order -> {
                    OrderInfoResponseDto orderInfoResponseDto = new OrderInfoResponseDto();
                    orderInfoResponseDto.setId(order.getId());
                    orderInfoResponseDto.setDate(order.getDate());
                    orderInfoResponseDto.setStatus(order.getStatus());
                    orderInfoResponseDto.setIdClient(order.getIdClient());
                    orderInfoResponseDto.setIdChef(order.getIdChef());
                    orderInfoResponseDto.setIdRestaurant(order.getIdRestaurant());
                    orderInfoResponseDto.setPlates(orderPlateList.stream()
                            .filter(orderPlate -> Objects.equals(orderPlate.getIdOrder(), order.getId()))
                            .map(orderPlate -> {
                                OrderPlateDto orderPlateDto = new OrderPlateDto();
                                orderPlateDto.setIdOrder(orderPlate.getIdOrder());
                                orderPlateDto.setIdPlate(orderPlate.getIdPlate());
                                orderPlateDto.setQuantity(orderPlate.getQuantity());
                                return orderPlateDto;
                            }).collect(Collectors.toList()));
                    return orderInfoResponseDto;
                }).collect(Collectors.toList());

        return new PageImpl<>(orderInfoResponseDtoList, orderPage.getPageable(), orderPage.getSize());
    }
}
