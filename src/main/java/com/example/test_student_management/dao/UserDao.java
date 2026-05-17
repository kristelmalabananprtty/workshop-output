package com.example.test_student_management.dao;

import com.example.test_student_management.util.DatabaseConnection;
import com.example.test_student_management.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public boolean authenticate(String username, String password) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                return PasswordUtil.verify(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
