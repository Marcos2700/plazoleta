package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.PlateRequestDto;
import com.pragma.powerup.application.dto.response.PlateInfoResponseDto;
import com.pragma.powerup.application.dto.response.PlateResponseDto;
import com.pragma.powerup.application.handler.IPlateHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plate")
public class PlateRestController {

    private final IPlateHandler plateHandler;

    @Operation(summary = "Create new plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New plate created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Plate already exist", content = @Content),
            @ApiResponse(responseCode = "409", description = "Plate is not associated with any restaurant", content = @Content)
    })
    @PostMapping("/owner")
    public ResponseEntity<Void> savePlate(@RequestBody PlateRequestDto plateRequestDto){
        plateHandler.savePlate(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all plates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all plates",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PlateResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PlateResponseDto>> getAllPlates(){
        return ResponseEntity.ok(plateHandler.getAllPlates());
    }

    @Operation(summary = "Update plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plate updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PutMapping("/owner")
    public ResponseEntity<Void> updatePlate(@RequestBody PlateRequestDto plateRequestDto, HttpServletRequest request){
        plateHandler.updatePlate(plateRequestDto, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Turn off/on plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plate updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PutMapping("/owner/{id}")
    public ResponseEntity<Void> turnOffOnPlate(@Parameter(description = "Id of the plate to be turned off") @PathVariable Long id, HttpServletRequest request){
        plateHandler.turnOffOnPlate(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "List plates by restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List plates",
                    content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlateInfoResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/client/list/{idRestaurant}")
    public ResponseEntity<Page<PlateInfoResponseDto>> listPlate(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "0") String idCategory,
                                                          @PathVariable Long idRestaurant){
        Long longIdCategory = Long.parseLong(idCategory);
        Page<PlateInfoResponseDto> responseDtoPage = plateHandler.listPlate(idRestaurant, longIdCategory, page, size);
        return ResponseEntity.ok(responseDtoPage);
    }


}
