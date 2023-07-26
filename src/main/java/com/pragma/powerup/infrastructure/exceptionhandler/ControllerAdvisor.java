package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.IsNotAOwnerException;
import com.pragma.powerup.domain.exception.NitNoNumericException;
import com.pragma.powerup.domain.exception.NumericRestaurantNameException;
import com.pragma.powerup.domain.exception.PhoneNumberNoNumericException;
import com.pragma.powerup.infrastructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(RestaurantAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistException(
            RestaurantAlreadyExistException ignoredRestaurantAlreadyExistException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_ALREADY_EXIST.getMessage()));
    }

    @ExceptionHandler(NitNoNumericException.class)
    public ResponseEntity<Map<String, String>> handleNitNoNumericException(
            NitNoNumericException ignoredNitNoNumericException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NIT_NO_NUMERIC.getMessage()));
    }

    @ExceptionHandler(PhoneNumberNoNumericException.class)
    public ResponseEntity<Map<String, String>> handlePhoneNumberNoNumericException(
            PhoneNumberNoNumericException ignoredPhoneNumberNoNumericException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PHONE_NUMBER_NO_NUMERIC.getMessage()));
    }

    @ExceptionHandler(NumericRestaurantNameException.class)
    public ResponseEntity<Map<String, String>> handleNumericRestaurantNameException(
            NumericRestaurantNameException ignoredNumericRestaurantNameException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NUMERIC_RESTAURANT_NAME.getMessage()));
    }

    @ExceptionHandler(IsNotAOwnerException.class)
    public ResponseEntity<Map<String, String>> handleIsNotAOwnerException(
            IsNotAOwnerException ignoredIsNotAOwnerException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.IS_NOT_A_OWNER.getMessage()));
    }

    @ExceptionHandler(PlateAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handlePlateAlreadyExistException(
            PlateAlreadyExistException ignoredPLateAlreadyExistException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PLATE_ALREADY_EXIST.getMessage()));
    }

    @ExceptionHandler(NoPlateToRestaurantAssociationException.class)
    public ResponseEntity<Map<String, String>> handleNoPlateToRestaurantAssociationException(
            NoPlateToRestaurantAssociationException ignoresNoPlateToRestaurantAssociationException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_PLATE_TO_RESTAURANT_ASSOCIATION.getMessage()));
    }

    @ExceptionHandler(NoOwnerPlateAssociationException.class)
    public ResponseEntity<Map<String, String>> handleNoOwnerPlateAssociationException(
            NoOwnerPlateAssociationException ignoredNoOwnerPlateAssociationException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_OWNER_PLATE_ASSOCIATION.getMessage()));
    }

    @ExceptionHandler(RestaurantNotExistException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotExistException(
            RestaurantNotExistException ignoredRestaurantNotExistException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_NOT_EXIST.getMessage()));
    }

    @ExceptionHandler(PlateNotExistException.class)
    public ResponseEntity<Map<String, String>> handlePlateNotExistException(
            PlateNotExistException ignoredPlateNotExistException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PLATE_NOT_EXIST.getMessage()));
    }

    @ExceptionHandler(OrderInProcessException.class)
    public ResponseEntity<Map<String, String>> handleOrderInProcessException(
            OrderInProcessException ignoredOrderInProcessException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ORDER_IN_PROCESS.getMessage()));
    }

}