package com.duoc.msvc_inventory.exceptions;

import org.springframework.http.HttpStatus;

public class InventoryExceptions extends RuntimeException {

    private final HttpStatus status;

    public InventoryExceptions(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public InventoryExceptions(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
