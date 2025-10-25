package ru.otus.exception;

public class QuestionLoadException extends RuntimeException {

    public QuestionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
