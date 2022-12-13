package ru.yandex.practicum.filmorate.exception;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

