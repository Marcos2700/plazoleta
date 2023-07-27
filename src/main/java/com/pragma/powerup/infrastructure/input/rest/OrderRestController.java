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
            @ApiResponse(responseCode = "404", description = "Order does not exist", content = @Content)
    })
    @PutMapping("/employee/assign/order/{id}")
    public ResponseEntity<Page<OrderInfoResponseDto>> updateOrderStatus(@PathVariable Long id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "") String status,
                                                                        HttpServletRequest request){
        Page<OrderInfoResponseDto> orderInfoResponseDtoPage = orderHandler.updateOrderStatus(id, status, page, size, request);
        return ResponseEntity.ok(orderInfoResponseDtoPage);
    }
}
