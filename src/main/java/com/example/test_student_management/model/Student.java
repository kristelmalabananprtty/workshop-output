package com.example.test_student_management.model;

import java.time.LocalDateTime;

public class Student {
    private int id;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String course;
    private int yearLevel;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Student() {}

    public Student(String studentNumber, String firstName, String lastName,
                   String course, int yearLevel, String email, String phone) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.yearLevel = yearLevel;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getYearLevelLabel() {
        return switch (yearLevel) {
            case 1 -> "1st Year";
            case 2 -> "2nd Year";
            case 3 -> "3rd Year";
            case 4 -> "4th Year";
            default -> yearLevel + "th Year";
        };
    }
}
