package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class InvalidURLException extends BaseMSEException {

    public InvalidURLException(String message) {
        super(message);
    }

    public InvalidURLException(String message, int status) {
        super(message, status);
    }

}
