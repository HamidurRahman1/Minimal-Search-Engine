package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class ConstraintViolationException extends BaseMSEException {

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, int status) {
        super(message, status);
    }

}
