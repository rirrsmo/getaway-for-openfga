package fr.rirrsmo.getaway.checker.exception;

public class InvalidCheckAnnotation extends RuntimeException {
    public InvalidCheckAnnotation(String message) {
        super(message);
    }

    public InvalidCheckAnnotation(String message, Throwable cause) {
        super(message, cause);
    }
}
