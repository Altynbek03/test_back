package kaz.altynbek.pratica.test_task.exception;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
