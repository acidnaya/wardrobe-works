package com.diploma.wardrobeservice.exceptions;

public class RequestNotProcessedException extends RuntimeException {
    public RequestNotProcessedException(String message) {
        super(message);
    }
}
