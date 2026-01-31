package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import model.User;
import repository.UserRepository;
import util.Validation;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    public static User loggedInUser = null;
    private UserRepository userRepository = new UserRepository();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // SIMPLE VALIDATION
        if (!Validation.notEmpty(username)) {
            messageLabel.setText("Username required");
            return;
        }
        if (!Validation.notEmpty(password)) {
            messageLabel.setText("Password required");
            return;
        }

        loggedInUser = userRepository.authenticate(username, password);

        if (loggedInUser != null) {
            goToDashboard();
        } else {
            messageLabel.setText("Wrong username/password");
        }
    }

    private void goToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/DashboardView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Student System - Dashboard");
            stage.centerOnScreen();


        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }
}