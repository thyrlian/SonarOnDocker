package com.basgeekball.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Detector {
    public static void main(String[] args) throws InterruptedException {
        String driver = null;
        String name = null;
        String url = null;
        String db = System.getProperty("db", "").toLowerCase();
        switch (db) {
            case "mysql":
                driver = "com.mysql.jdbc.Driver";
                name = "MySQL";
                url = "jdbc:mysql://mysql:3306/sonar";
                break;
            case "postgres":
                driver = "org.postgresql.Driver";
                name = "PostgreSQL";
                url = "jdbc:postgresql://postgres:5432/sonar";
                break;
            default:
                System.out.println("#==> ⚠ Can not identify DB.");
                System.exit(1);
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("#==> ⚠ Can not find JDBC driver for " + name + ".");
            e.printStackTrace();
            System.exit(1);
        }
        String user = "sonar";
        String password = "sonar";
        int retries = 120;
        long interval = 500;
        Connection connection;
        for (int i = 0; i < retries; i++) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                if (connection != null) {
                    System.out.println("#==> ⚡ DB connection is successful.");
                    return;
                }
            } catch (SQLException e) {
                System.out.println("#==> ⚠ Can not establish a connection to the DB.");
            }
            Thread.sleep(interval);
        }
        System.out.println("#==> ⚠ Failed to connect to the DB.  Quit.");
        System.exit(1);
    }
}
