package filmorateapp.model.validation;
//отдельный пакет в покете модель
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}