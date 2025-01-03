package org.rtss.mosad_backend.exceptions;

import java.util.Set;

public class ObjectNotValidException extends RuntimeException {

    private final Set<String> errorMessages;

    public ObjectNotValidException(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ObjectNotValidException(String message, Set<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }

    public ObjectNotValidException(String message, Throwable cause, Set<String> errorMessages) {
        super(message, cause);
        this.errorMessages = errorMessages;
    }

    public ObjectNotValidException(Throwable cause, Set<String> errorMessages) {
        super(cause);
        this.errorMessages = errorMessages;
    }

    public ObjectNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Set<String> errorMessages) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
