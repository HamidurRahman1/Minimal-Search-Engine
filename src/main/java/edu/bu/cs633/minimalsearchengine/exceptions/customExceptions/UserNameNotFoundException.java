package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

public class UserNameNotFoundException extends BaseMSEException {

    public UserNameNotFoundException(String msg) {
        super(msg);
    }

    public UserNameNotFoundException(String msg, int status) {
        super(msg, status);
    }
}