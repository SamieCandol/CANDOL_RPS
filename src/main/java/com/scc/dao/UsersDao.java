package com.scc.dao;

import com.scc.model.users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDao {

    public boolean registerUser(int user_id, String username, String password, String full_name, String roles, String status){

        String sql = "INSERT INTO users(user_id, username, password, full_name, roles, status) VALUES(?, ?, ?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, user_id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, full_name);
            pstmt.setString(5, roles != null ? roles.toUpperCase() : null);
            pstmt.setString(6, status);

            pstmt.executeUpdate();
            System.out.println("-> Successful registration, Waiting for Approval!");
            return true;
        } catch (SQLException e){
            System.err.println("Failed: " + e.getMessage());
            return false;
        }
    }

    // Gidugang nga login method para ma-check ang username ug password
    public users loginUser(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Kung naay makita, i-return ang user object gamit ang imong Record
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
        return null; // Balik og null kung walay makita o nasayop
    }
}