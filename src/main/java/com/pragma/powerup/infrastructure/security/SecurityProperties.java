package com.pragma.powerup.infrastructure.security;

public enum SecurityProperties {

    SECRET("qwertyuiopasdfghjklzxcvbnm123456789qwertyuiopasdfghjklzxcvbnm"),
    EXPIRATION_TIME("1000000000");

    private final String property;

    SecurityProperties(String property){
        this.property = property;
    }

    public String getProperty(){
        return this.property;
    }
}
