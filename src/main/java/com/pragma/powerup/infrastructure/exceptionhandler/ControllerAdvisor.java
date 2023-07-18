package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.IsNotAOwnerException;
import com.pragma.powerup.domain.exception.NitNoNumericException;
import com.pragma.powerup.domain.exception.NumericRestaurantNameException;
import com.pragma.powerup.domain.exception.PhoneNumberNoNumericException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.RestaurantAlreadyExistException;
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

}