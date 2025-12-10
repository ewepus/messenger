package ru.rutmiit.models.exceptions;

public class RecursionException extends RuntimeException {

    public RecursionException(String message) {
        super(message);
    }

    public RecursionException(String message, Throwable cause) {
        super(message, cause);
    }
}
