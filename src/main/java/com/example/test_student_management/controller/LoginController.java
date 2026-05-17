package com.example.test_student_management.controller;

import com.example.test_student_management.app.StudentApplication;
import com.example.test_student_management.dao.UserDao;
import com.example.test_student_management.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserDao userDao = new UserDao();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        if (userDao.authenticate(username, password)) {
            SessionManager.setCurrentUser(username);
            try {
                StudentApplication.switchToDashboard();
            } catch (Exception e) {
                errorLabel.setText("Error loading dashboard.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password.");
            passwordField.clear();
        }
    }
}
