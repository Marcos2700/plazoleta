package com.pragma.powerup.orderTests;

import com.pragma.powerup.application.dto.OrderPlateDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import com.pragma.powerup.application.handler.impl.OrderHandlerImpl;
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
import com.pragma.powerup.infrastructure.exception.NoReadyStatusBeforeException;
import com.pragma.powerup.infrastructure.exception.WrongPinException;
import com.pragma.powerup.infrastructure.input.feign.MessageFeignClient;
import com.pragma.powerup.infrastructure.input.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.input.feign.dto.ClientMessageDto;
import com.pragma.powerup.infrastructure.input.feign.dto.OwnerEmployeeRelation;
import com.pragma.powerup.infrastructure.input.feign.dto.UserDto;
import com.pragma.powerup.infrastructure.out.jpa.entity.PinOrder;
import com.pragma.powerup.infrastructure.out.jpa.repository.PinOrderRepository;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ExtendWith(SpringExtension.class)
class OrderHandlerTests {

    @InjectMocks
    OrderHandlerImpl orderHandler;

    @Mock
    IOrderServicePort orderServicePort;
    @Mock
    IOrderPlateServicePort orderPlateServicePort;
    @Mock
    IOrderRequestMapper orderRequestMapper;
    @Mock
    IOrderPlateMapper orderPlateMapper;
    @Mock
    IOrderResponseMapper responseMapper;
    @Mock
    HttpServletRequest request;
    @Mock
    UserFeignClient feignClient;
    @Mock
    IRestaurantServicePort restaurantServicePort;
    @Mock
    IUserDtoToClientDtoMapper userDtoToClientDtoMapper;
    @Mock
    MessageFeignClient messageFeignClient;
    @Mock
    PinOrderRepository pinOrderRepository;

    @Test
    void saveOrder(){
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        Order order =  new Order();
        OrderPlate orderPlate = new OrderPlate();
        OrderPlateDto orderPlateDto = new OrderPlateDto();
        List<OrderPlateDto> orderPlateDtoList = List.of(orderPlateDto);

        Mockito.when(orderRequestMapper.toOrder(orderRequestDto)).thenReturn(order);
        Mockito.when(orderServicePort.saveOrder(order)).thenReturn(order);
        Mockito.when(orderPlateMapper.toOrderPlate(orderPlateDto)).thenReturn(orderPlate);

        try {
            orderHandler.saveOrder(orderRequestDto);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void listOrders(){
        String bearer = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJxd2VlcnRlbXBsb3llZXNAZ21haWwuY29tIiwiZXhwIjoyNjkwNDY0MTA0LCJyb2xlcyI6WyJST0xFX0VNUExPWUVFIl0sIm5hbWUiOiJKb3NlIn0.arEUPpxE8t1QnP6FrNUtdYt8TOXTCvPZWMJTt7r-lduBmcz2CYlNgqlYmTeA_Jm9";
        TokenUtils tokenUtils = Mockito.mock(TokenUtils.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn(bearer);
        Mockito.when(tokenUtils.getEmail(bearer.replace("Bearer ", ""))).thenReturn("qweertemployees@gmail.com");

        UserDto employee = new UserDto();
        employee.setId(1L);

        Mockito.when(feignClient.getUserByEmail("qweertemployees@gmail.com")).thenReturn(employee);

        OwnerEmployeeRelation ownerEmployeeRelation = new OwnerEmployeeRelation();
        ownerEmployeeRelation.setIdOwner(1L);
        Mockito.when(feignClient.getOwnerEmployeeRelation(1L)).thenReturn(ownerEmployeeRelation);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        Mockito.when(restaurantServicePort.getRestaurantByOwnerId(1L)).thenReturn(restaurant);

        Pageable pageable = PageRequest.of(0, 10);
        Order order = new Order();
        List<Order> orderList = List.of(order);
        Page<Order> orderPage = new PageImpl<>(orderList, pageable, 1);
        Mockito.when(orderServicePort.listOrder(1L, "pending", pageable))
                .thenReturn(orderPage);

        OrderPlate orderPlate = new OrderPlate();
        List<OrderPlate> orderPlateList = List.of(orderPlate);

        OrderInfoResponseDto orderInfoResponseDto = new OrderInfoResponseDto();
        List<OrderInfoResponseDto> orderInfoResponseDtoList = List.of(orderInfoResponseDto);
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = new PageImpl<>(orderInfoResponseDtoList, pageable, 1);

        Mockito.when(responseMapper.toReponsePage(orderPage, orderPlateList))
                .thenReturn(orderInfoResponseDtoPage);

        Page<OrderInfoResponseDto> returnedOrderPage = orderHandler.listOrder("pending", 0, 10, request);

        Assertions.assertFalse(orderInfoResponseDtoPage.isEmpty());
    }

    @Test
    void updateStatus(){
        String bearer = "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJxd2VlcnRlbXBsb3llZXNAZ21haWwuY29tIiwiZXhwIjoyNjkwNDY0MTA0LCJyb2xlcyI6WyJST0xFX0VNUExPWUVFIl0sIm5hbWUiOiJKb3NlIn0.arEUPpxE8t1QnP6FrNUtdYt8TOXTCvPZWMJTt7r-lduBmcz2CYlNgqlYmTeA_Jm9";
        TokenUtils tokenUtils = Mockito.mock(TokenUtils.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn(bearer);
        Mockito.when(tokenUtils.getEmail(bearer.replace("Bearer ", ""))).thenReturn("qweertemployees@gmail.com");

        UserDto employee = new UserDto();
        employee.setId(1L);

        Mockito.when(feignClient.getUserByEmail("qweertemployees@gmail.com")).thenReturn(employee);

        OwnerEmployeeRelation ownerEmployeeRelation = new OwnerEmployeeRelation();
        ownerEmployeeRelation.setIdOwner(1L);
        Mockito.when(feignClient.getOwnerEmployeeRelation(1L)).thenReturn(ownerEmployeeRelation);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        Mockito.when(restaurantServicePort.getRestaurantByOwnerId(1L)).thenReturn(restaurant);

        Pageable pageable = PageRequest.of(0, 10);
        Order order = new Order();
        List<Order> orderList = List.of(order);
        Page<Order> orderPage = new PageImpl<>(orderList, pageable, 1);
        Mockito.when(orderServicePort.listOrder(1L, "pending", pageable))
                .thenReturn(orderPage);
        Mockito.when(orderServicePort.getOrder(1L)).thenReturn(order);

        OrderPlate orderPlate = new OrderPlate();
        List<OrderPlate> orderPlateList = List.of(orderPlate);

        OrderInfoResponseDto orderInfoResponseDto = new OrderInfoResponseDto();
        List<OrderInfoResponseDto> orderInfoResponseDtoList = List.of(orderInfoResponseDto);
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = new PageImpl<>(orderInfoResponseDtoList, pageable, 1);

        Mockito.when(responseMapper.toReponsePage(orderPage, orderPlateList))
                .thenReturn(orderInfoResponseDtoPage);


        Page<OrderInfoResponseDto> returnedOrderPage = orderHandler.updateOrderStatus(1L, "pending", 0, 10, request );

        Assertions.assertFalse(orderInfoResponseDtoPage.isEmpty());
    }

    @Test
    void setReadyStatus(){
        Order order = new Order();
        order.setIdClient(1L);
        Mockito.when(orderServicePort.getOrder(1L)).thenReturn(order);

        UserDto userDto = new UserDto();
        Mockito.when(feignClient.getUser(1L)).thenReturn(userDto);

        ClientMessageDto clientMessageDto = new ClientMessageDto();
        Mockito.when(userDtoToClientDtoMapper.toClientMessageDto(userDto, "000000")).thenReturn(clientMessageDto);

        try {
            orderHandler.setReadyStatus(1L);
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void setDeliveredStatus(){
        Order order = new Order();
        order.setIdClient(1L);
        order.setStatus(OrderStatus.READY.getStatus());
        Mockito.when(orderServicePort.getOrder(1L)).thenReturn(order);

        PinOrder pinOrder = new PinOrder();
        pinOrder.setPin("111111");
        Mockito.when(pinOrderRepository.findByIdOrder(1L)).thenReturn(pinOrder);

        try{
            orderHandler.setDeliveredStatus(1L, "111111");
            Assertions.assertTrue(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updatingToDeliveredWithWrongPreStatus() {
        Order order = new Order();
        order.setIdClient(1L);
        order.setStatus(OrderStatus.PENDING.getStatus());
        Mockito.when(orderServicePort.getOrder(1L)).thenReturn(order);

        PinOrder pinOrder = new PinOrder();
        pinOrder.setPin("111111");
        Mockito.when(pinOrderRepository.findByIdOrder(1L)).thenReturn(pinOrder);

        try {
            orderHandler.setDeliveredStatus(1L, "111111");
        } catch (NoReadyStatusBeforeException e) {
            e.printStackTrace();
            Assertions.assertInstanceOf(NoReadyStatusBeforeException.class, e);
        }
    }

    @Test
    void updatingToDeliveredWithWrongPin(){
        Order order = new Order();
        order.setIdClient(1L);
        order.setStatus(OrderStatus.READY.getStatus());
        Mockito.when(orderServicePort.getOrder(1L)).thenReturn(order);

        PinOrder pinOrder = new PinOrder();
        pinOrder.setPin("111111");
        Mockito.when(pinOrderRepository.findByIdOrder(1L)).thenReturn(pinOrder);

        try{
            orderHandler.setDeliveredStatus(1L, "111222");
        }
        catch (Exception e){
            e.printStackTrace();
            Assertions.assertInstanceOf(WrongPinException.class, e);
        }
    }
}
