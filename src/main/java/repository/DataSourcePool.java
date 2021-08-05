package repository;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourcePool {
    private static BasicDataSource basicDataSource = null;
    private static final String PATH_PROPERTIES_FILE = "src/main/resources/liquibase/liquibase.properties";

    private DataSourcePool() {
    }

    public static Connection getConnection() throws SQLException {
        if (basicDataSource == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(PATH_PROPERTIES_FILE));
            } catch (IOException e) {
                throw new RuntimeException("Произошла ошибка при чтении файла properties: " + PATH_PROPERTIES_FILE);
            }
            basicDataSource = new BasicDataSource();
            basicDataSource.setUsername(properties.getProperty("username"));
            basicDataSource.setPassword(properties.getProperty("password"));
            basicDataSource.setDriverClassName(properties.getProperty("driver"));
            basicDataSource.setUrl(properties.getProperty("url"));
            basicDataSource.setMinIdle(5);
            basicDataSource.setMinIdle(10);
        }
        return basicDataSource.getConnection();
    }
}
