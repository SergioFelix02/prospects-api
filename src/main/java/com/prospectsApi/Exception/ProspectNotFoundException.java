package com.prospectsApi.Exception;

public class ProspectNotFoundException extends RuntimeException {
    public ProspectNotFoundException(String message) {
        super(message);
    }
}
