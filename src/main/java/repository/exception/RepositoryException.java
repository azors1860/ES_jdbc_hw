package repository.exception;

/**
 * Предназначен для отображения ошибок БД.
 */
public class RepositoryException extends Exception {

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
