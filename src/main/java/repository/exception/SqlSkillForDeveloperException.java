package repository.exception;

import java.sql.SQLException;

/**
 * Предназначен для отображения ошибок БД при работе с навыками разработчика.
 */
public class SqlSkillForDeveloperException extends SQLException {
    public SqlSkillForDeveloperException(String message, Throwable cause) {
        super(message, cause);
    }
}
