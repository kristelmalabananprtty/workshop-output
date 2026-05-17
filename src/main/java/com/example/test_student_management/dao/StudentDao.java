package com.example.test_student_management.dao;

import com.example.test_student_management.model.Student;
import com.example.test_student_management.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public List<Student> search(String keyword) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = """
            SELECT * FROM students
            WHERE student_number ILIKE ? OR first_name ILIKE ?
               OR last_name ILIKE ? OR course ILIKE ?
            ORDER BY id
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) ps.setString(i, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public void insert(Student s) throws SQLException {
        String sql = """
            INSERT INTO students (student_number, first_name, last_name, course, year_level, email, phone)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStudentNumber());
            ps.setString(2, s.getFirstName());
            ps.setString(3, s.getLastName());
            ps.setString(4, s.getCourse());
            ps.setInt(5, s.getYearLevel());
            ps.setString(6, s.getEmail());
            ps.setString(7, s.getPhone());
            ps.executeUpdate();
        }
    }

    public void update(Student s) throws SQLException {
        String sql = """
            UPDATE students
            SET first_name=?, last_name=?, course=?, year_level=?, email=?, phone=?
            WHERE id=?
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getYearLevel());
            ps.setString(5, s.getEmail());
            ps.setString(6, s.getPhone());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentNumber(rs.getString("student_number"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setCourse(rs.getString("course"));
        s.setYearLevel(rs.getInt("year_level"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        Timestamp ca = rs.getTimestamp("created_at");
        if (ca != null) s.setCreatedAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) s.setUpdatedAt(ua.toLocalDateTime());
        return s;
    }
}
