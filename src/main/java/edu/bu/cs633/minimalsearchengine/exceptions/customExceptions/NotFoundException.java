package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class NotFoundException extends BaseMSEException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, int status) {
        super(message, status);
    }
}