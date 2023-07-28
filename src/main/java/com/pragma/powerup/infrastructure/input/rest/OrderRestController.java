package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.OrderInfoResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Client order list of plates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant does not exist", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plate does not exist", content = @Content),
            @ApiResponse(responseCode = "409", description = "There is a current order for this client", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<Void> makeOrder(@RequestBody OrderRequestDto orderRequestDto){
        orderHandler.saveOrder(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "List orders for employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List or Orders returned",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = OrderInfoResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/employee/list")
    public ResponseEntity<Page<OrderInfoResponseDto>> listOrders(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "") String status,
                                                           HttpServletRequest request){
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = orderHandler.listOrder(status, page, size, request);
        return ResponseEntity.ok(orderInfoResponseDtoPage);
    }

    @Operation(summary = "Assign order to chef")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List or Orders returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderInfoResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order does not exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Status cannot be in deliveded", content = @Content)
    })
    @PutMapping("/employee/assign/{id}")
    public ResponseEntity<Page<OrderInfoResponseDto>> updateOrderStatus(@PathVariable Long id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "") String status,
                                                                        HttpServletRequest request){
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = orderHandler.updateOrderStatus(id, status, page, size, request);
        return ResponseEntity.ok(orderInfoResponseDtoPage);
    }

    @Operation(summary = "Updating order status to ready")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated to ready", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order does not exist", content = @Content)
    })
    @PutMapping("/employee/ready/{idOrder}")
    public ResponseEntity<Void> updateOrderToReady(@PathVariable Long idOrder){
        orderHandler.setReadyStatus(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Updating order status to delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated to delivered", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order does not exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Status must be in ready before", content = @Content),
            @ApiResponse(responseCode = "403", description = "Pin is wrong", content = @Content)
    })
    @PutMapping("/employee/delivered/{idOrder}")
    public ResponseEntity<Void> updateOrderToDelivered(@PathVariable Long idOrder, @RequestParam String pin){
        orderHandler.setDeliveredStatus(idOrder, pin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Client cancel order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated to delivered", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order does not exist", content = @Content),
            @ApiResponse(responseCode = "409", description = "The order is in progress, so can't be canceled", content = @Content),
            @ApiResponse(responseCode = "403", description = "No order to client association", content = @Content)
    })
    @DeleteMapping("/client/cancel/{idOrder}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long idOrder, HttpServletRequest request){
        orderHandler.cancelOrder(idOrder, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
