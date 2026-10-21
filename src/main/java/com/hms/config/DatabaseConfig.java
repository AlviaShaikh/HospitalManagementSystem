package com.hms.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DatabaseConfig {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream in = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) PROPERTIES.load(in);
        } catch (IOException e) { throw new ExceptionInInitializerError(e); }
    }
    private DatabaseConfig() {}
    public static String url() { return value("DB_URL", "db.url", "jdbc:mysql://localhost:3306/hospital_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"); }
    public static String username() { return value("DB_USERNAME", "db.username", "root"); }
    public static String password() { return value("DB_PASSWORD", "db.password", "2805"); }
    private static String value(String env, String key, String fallback) { return System.getenv().getOrDefault(env, PROPERTIES.getProperty(key, fallback)); }
}
