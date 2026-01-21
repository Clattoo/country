package guru.qa.country.ex;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}