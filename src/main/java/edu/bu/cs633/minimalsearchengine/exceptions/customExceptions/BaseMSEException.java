package edu.bu.cs633.minimalsearchengine.exceptions.customExceptions;

import java.time.LocalDateTime;

public abstract class BaseMSEException extends RuntimeException {
    private LocalDateTime timestamp;
    private String errorMessage;
    private int status;

    public BaseMSEException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public BaseMSEException(String errorMessage, int status) {
        super();
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public BaseMSEException(LocalDateTime timestamp, String errorMessage, int status) {
        super();
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
