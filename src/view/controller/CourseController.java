package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import model.Course;
import service.interfaces.CourseService;
import service.implementations.CourseServiceImpl;
import util.Validation;

public class CourseController {
    @FXML private TextField codeField, nameField, creditsField;
    @FXML private Button addBtn, deleteBtn;
    @FXML private TableView<Course> courseTable;
    @FXML private Label titleLabel, messageLabel;

    private CourseService courseService = new CourseServiceImpl();
    private ObservableList<Course> courseList = FXCollections.observableArrayList();
    private boolean isManageMode = false;

    public void setManageMode(boolean isManage) {
        this.isManageMode = isManage;
        titleLabel.setText(isManage ? "Manage Courses" : "View Courses");
        addBtn.setVisible(isManage);
        deleteBtn.setVisible(isManage);
        setFieldsEditable(isManage);
    }

    @FXML
    public void initialize() {
        courseList.setAll(courseService.getAllCourses());
        courseTable.setItems(courseList);
        setManageMode(false);
    }

    @FXML
    private void handleAdd() {
        try {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String creditsStr = creditsField.getText().trim();

            if (!Validation.notEmpty(code)) {
                messageLabel.setText("Course code is required");
                return;
            }
            if (!Validation.notEmpty(name)) {
                messageLabel.setText("Course name is required");
                return;
            }
            if (!Validation.isInteger(creditsStr)) {
                messageLabel.setText("Credits must be a number");
                return;
            }

            int credits = Integer.parseInt(creditsStr);
            if (credits < 1 || credits > 6) {
                messageLabel.setText("Credits must be 1-6");
                return;
            }

            courseService.addCourse(code, name, credits);
            refreshTable();
            clearFields();
            messageLabel.setText("Course added!");
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setContentText("Delete course: " + selected.getCode() + "?");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                courseService.deleteCourse(selected.getCode());
                refreshTable();
                messageLabel.setText("Course deleted!");
            }
        } else {
            messageLabel.setText("Select a course first");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/DashboardView.fxml"));
            Stage stage = (Stage) courseTable.getScene().getWindow();

            Scene scene = new Scene(root,800,600);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    private void refreshTable() {
        courseList.setAll(courseService.getAllCourses());
        courseTable.refresh();
    }

    private void clearFields() {
        codeField.clear();
        nameField.clear();
        creditsField.clear();
    }

    private void setFieldsEditable(boolean editable) {
        codeField.setEditable(editable);
        nameField.setEditable(editable);
        creditsField.setEditable(editable);
    }
}