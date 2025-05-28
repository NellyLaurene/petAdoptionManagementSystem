package org.pet_adoption_system.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String db_url = "jdbc:postgresql://localhost:5432/pet_adoption_system_db";
    private static String db_user = "postgres";
    private static String db_password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(db_url, db_user, db_password);
    }
}
