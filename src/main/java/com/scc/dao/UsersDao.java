package com.scc.dao;

import com.scc.model.users;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    public boolean registerUser(int user_id, String username, String password, String full_name, String roles, String status) {
        String sql = "INSERT INTO users(user_id, username, password, full_name, roles, status) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, full_name);
            pstmt.setString(5, roles != null ? roles.toUpperCase() : null);
            pstmt.setString(6, status);

            pstmt.executeUpdate();
            System.out.println("-> Successful registration! Waiting for Admin approval.");
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to register: " + e.getMessage());
            return false;
        }
    }

    public users loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new users(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("roles"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Login Failed: " + e.getMessage());
        }
        return null;
    }

    public List<users> getAllUsers() { //[cite: 9]
        List<users> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                allUsers.add(new users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("roles"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }
        return allUsers;
    }

    public List<users> getPendingUsers() { //[cite: 9]
        List<users> pendingList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE status = 'PENDING'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pendingList.add(new users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("roles"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pending users: " + e.getMessage());
        }
        return pendingList;
    }

    public boolean approveUser(int userId) { //[cite: 9, 11]
        String sql = "UPDATE users SET status = 'APPROVED' WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error approving user: " + e.getMessage());
            return false;
        }
    }
}