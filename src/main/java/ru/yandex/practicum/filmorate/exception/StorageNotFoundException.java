package ru.yandex.practicum.filmorate.exception;

public class StorageNotFoundException extends NotFoundException {
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

