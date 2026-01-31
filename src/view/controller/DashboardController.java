package view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import service.implementations.StudentServiceImpl;

public class DashboardController {
    @FXML private Label welcomeLabel, roleLabel;
    @FXML private Button manageStudentsBtn, manageCoursesBtn, viewStudentsBtn, viewCoursesBtn;
    @FXML private Button enrollBtn, viewGradesBtn, addGradeBtn;

    @FXML
    public void initialize() {
        if (LoginController.loggedInUser != null) {
            String username = LoginController.loggedInUser.getUsername();
            String role = LoginController.loggedInUser.getRole();

            welcomeLabel.setText("Welcome, " + username + "!");
            roleLabel.setText("Role: " + role.toUpperCase());

            // ADMIN: Can manage everything
            // STUDENT: Can only view
            if (role.equals("admin")) {
                setupAdminDashboard();
            } else {
                setupStudentDashboard();
            }
        }
    }

    private void setupAdminDashboard() {

        manageStudentsBtn.setVisible(true);
        manageCoursesBtn.setVisible(true);
        viewStudentsBtn.setVisible(true);
        viewCoursesBtn.setVisible(true);
        enrollBtn.setVisible(true);
        viewGradesBtn.setVisible(true);
        addGradeBtn.setVisible(true);

        manageStudentsBtn.setText("Manage Students");
        manageCoursesBtn.setText("Manage Courses");
        enrollBtn.setText("Manage Enrollments");
        addGradeBtn.setText("Manage Grades");
    }

    private void setupStudentDashboard() {

        manageStudentsBtn.setVisible(false);
        manageCoursesBtn.setVisible(false);
        addGradeBtn.setVisible(false);

        viewStudentsBtn.setVisible(true);
        viewCoursesBtn.setVisible(true);
        enrollBtn.setVisible(true);
        viewGradesBtn.setVisible(true);

        viewStudentsBtn.setText("View Students");
        viewCoursesBtn.setText("View Courses");
        enrollBtn.setText("Enrollments");
        viewGradesBtn.setText("Grades");
    }

    // ADMIN FUNCTIONS
    @FXML private void handleManageStudents() { loadScene("/view/fxml/StudentView.fxml", "Manage Students", true); }
    @FXML private void handleManageCourses() { loadScene("/view/fxml/CourseView.fxml", "Manage Courses", true); }
    @FXML private void handleAddGrade() { loadScene("/view/fxml/GradeView.fxml", "Manage Grades", true); }

    // STUDENT FUNCTIONS (also visible to admin)
    @FXML private void handleViewStudents() { loadScene("/view/fxml/StudentView.fxml", "View Students", false); }
    @FXML private void handleViewCourses() { loadScene("/view/fxml/CourseView.fxml", "View Courses", false); }
    @FXML private void handleEnrollments() {
        if (LoginController.loggedInUser.getRole().equals("student")) {
            loadScene("/view/fxml/EnrollmentView.fxml", "Enrollments", false);
        } else {
            loadScene("/view/fxml/EnrollmentView.fxml", "Manage Enrollments", true);
        }
    }
    @FXML private void handleViewGrades() {
        if (LoginController.loggedInUser.getRole().equals("student")) {
            loadScene("/view/fxml/GradeView.fxml", "Grades", false);
        } else {
            loadScene("/view/fxml/GradeView.fxml", "View Grades", false);
        }
    }

    @FXML
    private void handleLogout() {
        LoginController.loggedInUser = null;
        loadScene("/view/fxml/LoginView.fxml", "Login", false);
    }

    private void loadScene(String fxmlPath, String title, boolean isManageMode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof StudentController) {
                ((StudentController) controller).setStudentService(new StudentServiceImpl());
                ((StudentController) controller).setManageMode(isManageMode);
            } else if (controller instanceof CourseController) {
                ((CourseController) controller).setManageMode(isManageMode);
            } else if (controller instanceof EnrollmentController) {
                ((EnrollmentController) controller).setManageMode(isManageMode);
            } else if (controller instanceof GradeController) {
                ((GradeController) controller).setManageMode(isManageMode);
            }

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root,800, 600);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();

        } catch (Exception e) {
            showError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}