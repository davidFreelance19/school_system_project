package proyectofinal.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvsAdapter {

    private static final Dotenv dotenv = Dotenv.load();

    public static final int PORT = Integer.parseInt(dotenv.get("PORT"));
    public static final String BACKEND_SERVER = dotenv.get("BACKEND_SERVER");
    
    public static final String SALT = dotenv.get("SALT");
    public static final String KEY = dotenv.get("KEY");
    public static final String JWT_SEED = dotenv.get("JWT_SEED");
    public static final String DATABASE_URL = dotenv.get("DB_URL");
    public static final String DATABASE_USERNAME = dotenv.get("DB_USERNAME");
    public static final String DATABASE_PASSWORD = dotenv.get("DB_PASSWORD");

    public static final String ROLE_STUDENT = dotenv.get("ROLE_STUDENT");
    public static final String ROLE_TEACHER = dotenv.get("ROLE_TEACHER");
    public static final String ROLE_ADMIN = dotenv.get("ROLE_ADMIN");

    public static final String MAIL_HOST = dotenv.get("MAIL_HOST");
    public static final int MAIL_PORT = Integer.parseInt(dotenv.get("MAIL_PORT"));
    public static final String MAIL_AUTH_USER = dotenv.get("MAIL_AUTH_USER");
    public static final String MAIL_AUTH_PASSWORD = dotenv.get("MAIL_AUTH_PASSWORD");
}
