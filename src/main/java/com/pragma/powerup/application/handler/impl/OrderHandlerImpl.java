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
import com.pragma.powerup.domain.api.IPinOrderServicePort;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.api.feign.IMessageFeignServicePort;
import com.pragma.powerup.domain.api.feign.ITraceabilityServicePort;
import com.pragma.powerup.domain.api.feign.IUserFeignServicePort;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.infrastructure.exception.*;
import com.pragma.powerup.infrastructure.out.feign.dto.ClientMessageDto;
import com.pragma.powerup.infrastructure.out.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private final IUserFeignServicePort userFeignClient;
    private final IMessageFeignServicePort messageFeignClient;
    private final IRestaurantServicePort restaurantServicePort;
    private final IUserDtoToClientDtoMapper userDtoToClientDtoMapper;
    private final IPinOrderServicePort pinOrderServicePort;
    private final ITraceabilityServicePort traceabilityFeign;
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
        UserDto client = userFeignClient.getUser(orderSaved.getIdClient());

        TraceabilityDto traceabilityDto = new TraceabilityDto();
        traceabilityDto.setIdOrder(orderSaved.getId());
        traceabilityDto.setDate(new Date());
        traceabilityDto.setIdClient(client.getId());
        traceabilityDto.setEmailClient(client.getEmail());
        traceabilityDto.setIdEmployee(orderSaved.getIdChef());
        traceabilityDto.setCurrentState(orderSaved.getStatus());
        traceabilityDto.setPreviousState("None");
        traceabilityDto.setEmailEmployee("None");

        traceabilityFeign.saveTrace(traceabilityDto);
    }

    @Override
    public Page<OrderInfoResponseDto> assignOrder(Long idOrder, String status, int page, int size, HttpServletRequest request) {
        Long idChef = this.getIdUser(request);

        UserDto employee = userFeignClient.getUser(idChef);

        TraceabilityDto traceabilityDto = new TraceabilityDto();
        traceabilityDto.setIdEmployee(employee.getId());
        traceabilityDto.setEmailEmployee(employee.getEmail());

        Order order = orderServicePort.getOrder(idOrder);
        if(order.getStatus().equals(OrderStatus.DELIVERED.getStatus())){
            throw new OrderStatusException();
        }
        traceabilityDto.setIdOrder(order.getId());
        traceabilityDto.setPreviousState(order.getStatus());

        UserDto client = userFeignClient.getUser(order.getIdClient());

        traceabilityDto.setIdClient(client.getId());
        traceabilityDto.setEmailClient(client.getEmail());

        order.setIdChef(idChef);
        order.setStatus(OrderStatus.IN_PREPARATION.getStatus());

        traceabilityDto.setCurrentState(order.getStatus());

        orderServicePort.updateStatus(order);

        Long idRestaurant = this.getRestaurantId(request);
        Pageable pageable = PageRequest.of(page, size);

        traceabilityDto.setDate(new Date());
        traceabilityFeign.saveTrace(traceabilityDto);

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
        TraceabilityDto traceabilityDto = new TraceabilityDto();

        Order order = orderServicePort.getOrder(idOrder);
        traceabilityDto.setIdOrder(order.getId());
        traceabilityDto.setPreviousState(order.getStatus());
        order.setStatus(OrderStatus.READY.getStatus());
        traceabilityDto.setCurrentState(order.getStatus());
        orderServicePort.updateStatus(order);

        UserDto client = userFeignClient.getUser(order.getIdClient());
        traceabilityDto.setIdClient(client.getId());
        traceabilityDto.setEmailClient(client.getEmail());

        UserDto employee = userFeignClient.getUser(order.getIdChef());
        traceabilityDto.setIdEmployee(employee.getId());
        traceabilityDto.setEmailEmployee(employee.getEmail());

        traceabilityDto.setDate(new Date());
        traceabilityFeign.saveTrace(traceabilityDto);

        ClientMessageDto clientUser = userDtoToClientDtoMapper.toClientMessageDto(userFeignClient.getUser(order.getIdClient()), generatePin());
        messageFeignClient.sendMessage(clientUser);

        PinOrder pinOrder = new PinOrder();
        pinOrder.setIdOrder(idOrder);
        pinOrder.setPin(clientUser.getPin());
        pinOrderServicePort.savePinOrder(pinOrder);

    }

    @Override
    public void setDeliveredStatus(Long idOrder, String pin) {
        TraceabilityDto traceabilityDto = new TraceabilityDto();

        Order order = orderServicePort.getOrder(idOrder);
        if(!order.getStatus().equals(OrderStatus.READY.getStatus())){
            throw new NoReadyStatusBeforeException();
        }

        traceabilityDto.setIdOrder(order.getId());
        traceabilityDto.setPreviousState(order.getStatus());

        UserDto client = userFeignClient.getUser(order.getIdClient());

        traceabilityDto.setIdClient(client.getId());
        traceabilityDto.setEmailClient(client.getEmail());

        UserDto employee = userFeignClient.getUser(order.getIdChef());

        traceabilityDto.setIdEmployee(employee.getId());
        traceabilityDto.setEmailEmployee(employee.getEmail());

        PinOrder pinOrder = pinOrderServicePort.getByOrder(idOrder);
        if(!Objects.equals(pinOrder.getPin(), pin)){
            throw new WrongPinException();
        }
        pinOrderServicePort.deletePinOrder(pinOrder.getId());

        order.setStatus(OrderStatus.DELIVERED.getStatus());
        traceabilityDto.setCurrentState(order.getStatus());
        orderServicePort.updateStatus(order);

        traceabilityDto.setDate(new Date());
        traceabilityFeign.saveTrace(traceabilityDto);
    }

    @Override
    public void cancelOrder(Long idOrder, HttpServletRequest request) {
        Long idClient = this.getIdUser(request);
        UserDto client = userFeignClient.getUser(idClient);
        Order order = orderServicePort.getOrder(idOrder);
        if(!Objects.equals(order.getIdClient(), idClient)){
            throw new NoOrderClientAssociationException();
        }

        if(!order.getStatus().equals(OrderStatus.PENDING.getStatus())){
            messageFeignClient.notifyNotIsPossibleToCancel(client.getPhoneNumber());
            throw new OrderNotCancelableException();
        }

        order.setStatus(OrderStatus.CANCELED.getStatus());
        orderServicePort.updateStatus(order);
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

    private Long getIdUser(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ","");
        String email = tokenUtils.getEmail(token);
        UserDto user = userFeignClient.getUserByEmail(email);

        return user.getId();
    }


}
