package org.syscom.module_achat.exception.validation;

public class NotAuthorizedToDeleteException extends RuntimeException {
    public NotAuthorizedToDeleteException(String message) {
        super(message);
    }
}
