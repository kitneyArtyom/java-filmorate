package ru.yandex.practicum.filmorate.exception;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException() {
    }

    public StorageNotFoundException(String message) {
        super(message);
    }

    public StorageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

