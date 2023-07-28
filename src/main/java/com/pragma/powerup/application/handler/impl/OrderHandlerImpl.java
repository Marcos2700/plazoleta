package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderPlateMapper;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.mapper.IOrderResponseMapper;
import com.pragma.powerup.application.mapper.IUserDtoToClientDtoMapper;
import com.pragma.powerup.domain.api.IOrderPlateServicePort;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.Order;
import com.pragma.powerup.domain.model.OrderPlate;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.input.feign.MessageFeignClient;
import com.pragma.powerup.infrastructure.input.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.input.feign.dto.ClientMessageDto;
import com.pragma.powerup.infrastructure.input.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.input.feign.dto.UserDto;
import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrder;
import com.pragma.powerup.infrastructure.out.jpa.repository.PinOrderRepository;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderPlateServicePort orderPlateServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderPlateMapper orderPlateMapper;
    private final UserFeignClient userFeignClient;
    private final MessageFeignClient messageFeignClient;
    private final IRestaurantServicePort restaurantServicePort;
    private final IUserDtoToClientDtoMapper userDtoToClientDtoMapper;
    private final PinOrderRepository pinOrderRepository;
    private final TokenUtils tokenUtils = new TokenUtils();

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        order.setStatus(OrderStatus.PENDING.getStatus());
        Order orderSaved = orderServicePort.saveOrder(order);

        List<OrderPlateDto> orderPlateDtoList = orderRequestDto.getOrders();

        orderPlateDtoList.forEach(orderPlateDto -> {
                    orderPlateDto.setIdOrder(orderSaved.getId());
                    OrderPlate orderPlate = orderPlateMapper.toOrderPlate(orderPlateDto);
                    orderPlateServicePort.saveOrderPlate(orderPlate, order);
                });
    }

    @Override
    public Page<OrderInfoResponseDto> updateOrderStatus(Long idOrder, String status, int page, int size, HttpServletRequest request) {
        Long idChef = this.getIdChef(request);

        Order order = orderServicePort.getOrder(idOrder);
        order.setIdChef(idChef);
        order.setStatus(OrderStatus.IN_PREPARATION.getStatus());

        orderServicePort.updateStatus(order);

        Long idRestaurant = this.getRestaurantId(request);
        Pageable pageable = PageRequest.of(page, size);

        return this.getOrdersResponsePage(idRestaurant, pageable, status);
    }

    @Override
    public Page<OrderInfoResponseDto> listOrder(String status, int page, int size, HttpServletRequest request) {
        Long idRestaurant = this.getRestaurantId(request);
        Pageable pageable = PageRequest.of(page, size);

        return this.getOrdersResponsePage(idRestaurant, pageable, status);
    }

    @Override
    public void setReadyStatus(Long idOrder) {
        Order order = orderServicePort.getOrder(idOrder);
        order.setStatus(OrderStatus.READY.getStatus());
        orderServicePort.updateStatus(order);

        ClientMessageDto clientUser = userDtoToClientDtoMapper.toClientMessageDto(userFeignClient.getUser(order.getIdClient()), generatePin());
        messageFeignClient.sendMessage(clientUser);

        PinOrder pinOrder = new PinOrder();
        pinOrder.setIdOrder(idOrder);
        pinOrder.setPin(clientUser.getPin());
        pinOrderRepository.save(pinOrder);

    }

    private Page<OrderInfoResponseDto> getOrdersResponsePage(Long idRestaurant, Pageable pageable, String status){
        Page<Order> orderPage = orderServicePort.listOrder(idRestaurant, status, pageable);
        List<OrderPlate> orderPlateList = orderPage.getContent().stream()
                .flatMap(order -> orderPlateServicePort.findAllByOrderId(order.getId()).stream())
                .collect(Collectors.toList());
        return orderResponseMapper.toReponsePage(orderServicePort.listOrder(idRestaurant, status, pageable), orderPlateList);
    }

    private String generatePin(){
        SecureRandom secureRandom = new SecureRandom();
        int pinLength = 6;
        StringBuilder stringBuilder = new StringBuilder(pinLength);
        for(int i = 0; i < pinLength; i++){
            String pinChars = "0123456789";
            int randomIndex = secureRandom.nextInt(pinChars.length());
            char randomChar = pinChars.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    private Long getRestaurantId(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ","");
        String email = tokenUtils.getEmail(token);
        UserDto employee = userFeignClient.getUserByEmail(email);

        OwnerEmployeeRelation ownerEmployeeRelation = userFeignClient.getOwnerEmployeeRelation(employee.getId());
        Restaurant restaurant = restaurantServicePort.getRestaurantByOwnerId(ownerEmployeeRelation.getIdOwner());

        return restaurant.getId();
    }

    private Long getIdChef(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ","");
        String email = tokenUtils.getEmail(token);
        UserDto employee = userFeignClient.getUserByEmail(email);

        return employee.getId();
    }


}
