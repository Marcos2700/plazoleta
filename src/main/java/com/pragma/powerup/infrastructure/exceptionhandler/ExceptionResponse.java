package com.pragma.powerup.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    RESTAURANT_ALREADY_EXIST("There is a restaurant with that id"),
    PHONE_NUMBER_NO_NUMERIC("Phone number must be numeric, only is accepted an initial + sign"),
    NIT_NO_NUMERIC("NIT must be only numeric"),
    NUMERIC_RESTAURANT_NAME("Restaurant name can`t be only numeric"),
    IS_NOT_A_OWNER("Restaurant owner must have owner role"),
    PLATE_ALREADY_EXIST("There is a plate with that name"),
    NO_PLATE_TO_RESTAURANT_ASSOCIATION("There is not a restaurant associated with that plate"),
    NO_OWNER_PLATE_ASSOCIATION("Restaurant's plate is not associated with owner"),
    RESTAURANT_NOT_EXIST("Restaurant does not exits"),
    PLATE_NOT_EXIST("Plate does not exist"),
    ORDER_IN_PROCESS("There is a order in process for this client"),
    ORDER_NOT_EXIST("Order not exist");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}