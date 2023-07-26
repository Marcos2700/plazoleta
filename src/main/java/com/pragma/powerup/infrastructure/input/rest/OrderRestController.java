package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
