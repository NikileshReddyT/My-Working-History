package com.jfsd.exit_portal_backend.RequestBodies;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

