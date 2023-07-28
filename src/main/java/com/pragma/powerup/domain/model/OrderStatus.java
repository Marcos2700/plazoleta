package com.pragma.powerup.domain.model;

public enum OrderStatus {
    IN_PREPARATION("in_preparation"),
    PENDING("pending"),
    READY("ready"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
