package com.visualart.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName + " not found with id " + identifier);
    }
}
//Exception