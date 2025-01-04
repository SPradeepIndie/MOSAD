package org.rtss.mosad_backend.exceptions;

//Stock item deletion exception
public class ItemDeletionException extends RuntimeException {
    public ItemDeletionException(String message) {
        super(message);
    }
}
