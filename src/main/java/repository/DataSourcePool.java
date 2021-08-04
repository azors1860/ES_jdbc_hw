package repository;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourcePool {
    private static BasicDataSource basicDataSource = null;

    private DataSourcePool() {
    }

    public static Connection getConnection() throws SQLException {
        if (basicDataSource == null) {
            SettingDB settingDB = new SettingDB("src/main/resources/liquibase/liquibase.properties");
            basicDataSource = new BasicDataSource();
            basicDataSource.setUsername(settingDB.userName);
            basicDataSource.setPassword(settingDB.password);
            basicDataSource.setDriverClassName(settingDB.driverClassName);
            basicDataSource.setUrl(settingDB.url);
            basicDataSource.setMinIdle(5);
            basicDataSource.setMinIdle(10);
        }
        return basicDataSource.getConnection();
    }

    private static class SettingDB {
        private final String pathFile;
        private String userName;
        private String password;
        private String driverClassName;
        private String url;

        public SettingDB(String pathFile) {
            this.pathFile = pathFile;
            readFile();
        }

        private void readFile() {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(pathFile));
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line.contains("url=")) {
                        url = line.replace("url=", "");
                    }
                    if (line.contains("username=")) {
                        userName = line.replace("username=", "");
                    }
                    if (line.contains("password=")) {
                        password = line.replace("password=", "");
                    }
                    if (line.contains("driver=")) {
                        driverClassName = line.replace("driver=", "");
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Не найден файл: " + pathFile);
            } catch (IOException e) {
                throw new RuntimeException("Возникла ошибка при чтении файла: " + pathFile);
            }
        }
    }
}
