package com.example.test_student_management.util;

public class SessionManager {
    private static String currentUser;

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
