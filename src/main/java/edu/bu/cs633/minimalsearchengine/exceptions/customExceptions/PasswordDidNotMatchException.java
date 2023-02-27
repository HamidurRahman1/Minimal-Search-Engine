package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class PasswordDidNotMatchException extends BaseMSEException {

    public PasswordDidNotMatchException(String message) {
        super(message);
    }

    public PasswordDidNotMatchException(String message, int status) {
        super(message, status);
    }

}
