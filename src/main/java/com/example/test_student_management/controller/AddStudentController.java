package com.example.test_student_management.controller;

import com.example.test_student_management.dao.StudentDao;
import com.example.test_student_management.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    @FXML private Label formTitleLabel;
    @FXML private TextField studentNumberField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> courseCombo;
    @FXML private ComboBox<Integer> yearCombo;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Label statusLabel;

    private final StudentDao studentDao = new StudentDao();
    private Student studentToEdit = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseCombo.getItems().addAll(
            "BSCS", "BSIT", "BSIS", "BSECE", "BSBA", "BSA", "BSN", "BSME", "BSCE"
        );
        yearCombo.getItems().addAll(1, 2, 3, 4);
        yearCombo.setValue(1);
    }

    public void setStudentForEdit(Student student) {
        this.studentToEdit = student;
        formTitleLabel.setText("Edit Student");
        studentNumberField.setText(student.getStudentNumber());
        studentNumberField.setDisable(true); // student number is immutable
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        courseCombo.setValue(student.getCourse());
        yearCombo.setValue(student.getYearLevel());
        emailField.setText(student.getEmail());
        phoneField.setText(student.getPhone());
    }

    @FXML
    private void handleSave() {
        String studentNumber = studentNumberField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String course = courseCombo.getValue();
        Integer year = yearCombo.getValue();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (studentNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                || course == null || year == null) {
            statusLabel.setText("Please fill in all required fields.");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        try {
            if (studentToEdit == null) {
                Student s = new Student(studentNumber, firstName, lastName, course, year, email, phone);
                studentDao.insert(s);
                statusLabel.setText("Student added successfully!");
            } else {
                studentToEdit.setFirstName(firstName);
                studentToEdit.setLastName(lastName);
                studentToEdit.setCourse(course);
                studentToEdit.setYearLevel(year);
                studentToEdit.setEmail(email);
                studentToEdit.setPhone(phone);
                studentDao.update(studentToEdit);
                statusLabel.setText("Student updated successfully!");
            }
            statusLabel.setStyle("-fx-text-fill: #27ae60;");

            // Close after short delay
            new Thread(() -> {
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(this::handleCancel);
            }).start();

        } catch (SQLException e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
