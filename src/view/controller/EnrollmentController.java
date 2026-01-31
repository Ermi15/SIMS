package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import model.Enrollment;
import service.interfaces.EnrollmentService;
import service.implementations.EnrollmentServiceImpl;
import util.Validation;

public class EnrollmentController {
    @FXML private TextField idField, studentIdField, courseCodeField, semesterField;
    @FXML private Button addBtn, deleteBtn;
    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private Label titleLabel, messageLabel;

    private EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private ObservableList<Enrollment> enrollmentList = FXCollections.observableArrayList();
    private boolean isManageMode = false;

    public void setManageMode(boolean isManage) {
        this.isManageMode = isManage;
        titleLabel.setText(isManage ? "Manage Enrollments" : "Enrollments");
        addBtn.setVisible(isManage);
        deleteBtn.setVisible(isManage);
        setFieldsEditable(isManage);
    }

    @FXML
    public void initialize() {
        try {
            System.out.println("EnrollmentController initializing...");
            refreshTable();
            setManageMode(false);
            System.out.println("EnrollmentController initialized successfully");
        } catch (Exception e) {
            System.out.println("ERROR in EnrollmentController.initialize: " + e.getMessage());
            e.printStackTrace();
            messageLabel.setText("Error loading enrollments");
        }
    }

    @FXML
    private void handleAdd() {
        try {
            String id = idField.getText().trim();
            String studentId = studentIdField.getText().trim();
            String courseCode = courseCodeField.getText().trim();
            String semester = semesterField.getText().trim();

            if (!Validation.notEmpty(id)) {
                messageLabel.setText("Enrollment ID is required");
                return;
            }
            if (!Validation.notEmpty(studentId)) {
                messageLabel.setText("Student ID is required");
                return;
            }
            if (!Validation.notEmpty(courseCode)) {
                messageLabel.setText("Course code is required");
                return;
            }
            if (!Validation.notEmpty(semester)) {
                messageLabel.setText("Semester is required");
                return;
            }

            enrollmentService.addEnrollment(id, studentId, courseCode, semester);
            refreshTable();
            clearFields();
            messageLabel.setText("Enrollment added!");
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Delete enrollment " + selected.getId() + "?");

                if (confirm.showAndWait().get() == ButtonType.OK) {
                    enrollmentService.deleteEnrollment(selected.getId());
                    refreshTable();
                    messageLabel.setText("Enrollment deleted!");
                }
            } else {
                messageLabel.setText("Select an enrollment first");
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/DashboardView.fxml"));
            Stage stage = (Stage) enrollmentTable.getScene().getWindow();

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
        try {
            enrollmentList.setAll(enrollmentService.getAllEnrollments());
            enrollmentTable.setItems(enrollmentList);

            if (enrollmentList.isEmpty()) {
                messageLabel.setText("No enrollments found");
            } else {
                messageLabel.setText("Showing " + enrollmentList.size() + " enrollments");
            }
        } catch (Exception e) {
            System.out.println("ERROR in refreshTable: " + e.getMessage());
            messageLabel.setText("Error loading data");
        }
    }

    private void clearFields() {
        idField.clear();
        studentIdField.clear();
        courseCodeField.clear();
        semesterField.clear();
    }

    private void setFieldsEditable(boolean editable) {
        idField.setEditable(editable);
        studentIdField.setEditable(editable);
        courseCodeField.setEditable(editable);
        semesterField.setEditable(editable);
    }
}