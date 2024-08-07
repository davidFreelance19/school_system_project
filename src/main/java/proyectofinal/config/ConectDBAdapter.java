package proyectofinal.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectDBAdapter {
    private static String url = EnvsAdapter.DATABASE_URL;
    private static String username = EnvsAdapter.DATABASE_USERNAME;
    private static String password = EnvsAdapter.DATABASE_PASSWORD;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
