package com.example.test_student_management.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        switchToLogin();
        stage.setTitle("Student Management System");
        stage.setResizable(true);
        stage.show();
    }

    public static void switchToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            StudentApplication.class.getResource("/com/example/test_student_management/login.fxml")
        );
        Scene scene = new Scene(loader.load(), 480, 360);
        scene.getStylesheets().add(
            StudentApplication.class.getResource("/com/example/test_student_management/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static void switchToDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            StudentApplication.class.getResource("/com/example/test_student_management/dashboard.fxml")
        );
        Scene scene = new Scene(loader.load(), 960, 640);
        scene.getStylesheets().add(
            StudentApplication.class.getResource("/com/example/test_student_management/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static void switchToAddStudent() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            StudentApplication.class.getResource("/com/example/test_student_management/add_student.fxml")
        );
        Scene scene = new Scene(loader.load(), 520, 560);
        scene.getStylesheets().add(
            StudentApplication.class.getResource("/com/example/test_student_management/styles.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
