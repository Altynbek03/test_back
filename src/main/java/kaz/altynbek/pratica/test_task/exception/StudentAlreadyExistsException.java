package kaz.altynbek.pratica.test_task.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
