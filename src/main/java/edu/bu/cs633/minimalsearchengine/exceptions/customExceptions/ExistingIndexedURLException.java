package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class ExistingIndexedURLException extends BaseMSEException {

    public ExistingIndexedURLException(String message) {
        super(message);
    }

    public ExistingIndexedURLException(String message, int status) {
        super(message, status);
    }

}