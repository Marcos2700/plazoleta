package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantInfoResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Create new restaurant")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "New restaurant created.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exist", content = @Content)
    })
    @PostMapping("/admin")
    public ResponseEntity<Void> saveRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto){
        restaurantHandler.saveRestaurant(restaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All restaurants",
                content = @Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/admin")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurant(){
        return ResponseEntity.ok(restaurantHandler.getAllRestaurant());
    }

    @Operation(summary = "List restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list restaurants in order",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = RestaurantInfoResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/client/list")
    public ResponseEntity<Page<RestaurantInfoResponseDto>> listRestaurant(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        Page<RestaurantInfoResponseDto> responseDtoPage = restaurantHandler.listRestaurant(page, size);
        return ResponseEntity.ok(responseDtoPage);
    }
}
