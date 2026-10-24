package com.scc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "full_name TEXT NOT NULL, " +
                "roles TEXT NOT NULL, " +
                "status TEXT NOT NULL" +
                ");";

        String createPropertiesTable = "CREATE TABLE IF NOT EXISTS properties (" +
                "property_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "unit_number TEXT NOT NULL, " +
                "monthly_rent REAL NOT NULL, " +
                "unit_status TEXT NOT NULL" +
                ");";

        String createLeasesTable = "CREATE TABLE IF NOT EXISTS leases (" +
                "lease_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "property_id INTEGER, " +
                "tenant_id INTEGER, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "FOREIGN KEY(property_id) REFERENCES properties(property_id), " +
                "FOREIGN KEY(tenant_id) REFERENCES users(user_id)" +
                ");";

        String createPaymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
                "payment_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lease_id INTEGER, " +
                "payment_date TEXT, " +
                "amount_paid REAL, " +
                "payment_status TEXT, " +
                "FOREIGN KEY(lease_id) REFERENCES leases(lease_id)" +
                ");";

        String createActivityLogsTable = "CREATE TABLE IF NOT EXISTS activity_logs (" +
                "log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "action TEXT, " +
                "timestamp TEXT, " +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createPropertiesTable);
            stmt.execute(createLeasesTable);
            stmt.execute(createPaymentsTable);
            stmt.execute(createActivityLogsTable);
            System.out.println("-> Database tables initialized successfully with Foreign Keys enabled.");
        } catch (SQLException e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
        }
    }
}