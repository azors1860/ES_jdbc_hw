package repository.exception;
/**
 * Предназначен для отображения отсутствия объекта в БД.
 */
public class UnknownItemException extends Exception{
    public UnknownItemException(String message) {
        super(message);
    }
}
