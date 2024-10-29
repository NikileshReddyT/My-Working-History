package com.jfsd.exit_portal_backend.RequestBodies;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
