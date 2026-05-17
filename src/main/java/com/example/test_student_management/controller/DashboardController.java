package com.example.test_student_management.controller;

import com.example.test_student_management.app.StudentApplication;
import com.example.test_student_management.dao.StudentDao;
import com.example.test_student_management.model.Student;
import com.example.test_student_management.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label totalStudentsLabel;
    @FXML private TextField searchField;

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colStudentNo;
    @FXML private TableColumn<Student, String> colFirstName;
    @FXML private TableColumn<Student, String> colLastName;
    @FXML private TableColumn<Student, String> colCourse;
    @FXML private TableColumn<Student, Integer> colYear;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, String> colPhone;

    private final StudentDao studentDao = new StudentDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome, " + SessionManager.getCurrentUser() + "!");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentNo.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadStudents();
    }

    private void loadStudents() {
        try {
            List<Student> students = studentDao.findAll();
            studentTable.setItems(FXCollections.observableArrayList(students));
            totalStudentsLabel.setText("Total Students: " + students.size());
        } catch (SQLException e) {
            showError("Database Error", "Could not load students: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim();
        try {
            List<Student> results = keyword.isEmpty()
                    ? studentDao.findAll()
                    : studentDao.search(keyword);
            studentTable.setItems(FXCollections.observableArrayList(results));
            totalStudentsLabel.setText("Results: " + results.size());
        } catch (SQLException e) {
            showError("Search Error", e.getMessage());
        }
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        loadStudents();
    }

    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/test_student_management/add_student.fxml")
            );
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(StudentApplication.getPrimaryStage());
            dialog.setTitle("Add New Student");
            Scene scene = new Scene(loader.load(), 520, 560);
            scene.getStylesheets().add(
                getClass().getResource("/com/example/test_student_management/styles.css").toExternalForm()
            );
            dialog.setScene(scene);
            dialog.showAndWait();
            loadStudents();
        } catch (IOException e) {
            showError("Error", "Could not open add student form.");
        }
    }

    @FXML
    private void handleEdit() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("No Selection", "Please select a student to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/test_student_management/add_student.fxml")
            );
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(StudentApplication.getPrimaryStage());
            dialog.setTitle("Edit Student");
            Scene scene = new Scene(loader.load(), 520, 560);
            scene.getStylesheets().add(
                getClass().getResource("/com/example/test_student_management/styles.css").toExternalForm()
            );
            dialog.setScene(scene);

            AddStudentController controller = loader.getController();
            controller.setStudentForEdit(selected);

            dialog.showAndWait();
            loadStudents();
        } catch (IOException e) {
            showError("Error", "Could not open edit form.");
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("No Selection", "Please select a student to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete " + selected.getFullName() + "?");
        confirm.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                studentDao.delete(selected.getId());
                loadStudents();
            } catch (SQLException e) {
                showError("Delete Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.clear();
        try {
            StudentApplication.switchToLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
