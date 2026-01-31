package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import model.Grade;
import service.interfaces.GradeService;
import service.implementations.GradeServiceImpl;
import util.Validation;

public class GradeController {
    @FXML private TextField enrollmentIdField, marksField, gradeField;
    @FXML private Button addBtn, deleteBtn;
    @FXML private TableView<Grade> gradeTable;
    @FXML private Label titleLabel, messageLabel;

    private GradeService gradeService = new GradeServiceImpl();
    private ObservableList<Grade> gradeList = FXCollections.observableArrayList();
    private boolean isManageMode = false;

    public void setManageMode(boolean isManage) {
        this.isManageMode = isManage;
        titleLabel.setText(isManage ? "Manage Grades" : "My Grades");
        addBtn.setVisible(isManage);
        deleteBtn.setVisible(isManage);
        setFieldsEditable(isManage);
    }

    @FXML
    public void initialize() {
        try {
            System.out.println("GradeController initializing...");
            refreshTable();
            setManageMode(false);
            System.out.println("GradeController initialized successfully");
        } catch (Exception e) {
            System.out.println("ERROR in GradeController.initialize: " + e.getMessage());
            e.printStackTrace();
            messageLabel.setText("Error loading grades");
        }
    }

    @FXML
    private void handleAdd() {
        try {
            String enrollmentId = enrollmentIdField.getText().trim();
            String marksStr = marksField.getText().trim();
            String grade = gradeField.getText().trim();

            if (!Validation.notEmpty(enrollmentId)) {
                messageLabel.setText("Enrollment ID is required");
                return;
            }
            if (!Validation.isNumber(marksStr)) {
                messageLabel.setText("Marks must be a number");
                return;
            }
            if (!Validation.notEmpty(grade)) {
                messageLabel.setText("Grade is required");
                return;
            }

            double marks = Double.parseDouble(marksStr);
            if (marks < 0 || marks > 100) {
                messageLabel.setText("Marks must be 0-100");
                return;
            }

            gradeService.addGrade(enrollmentId, marks, grade);
            refreshTable();
            clearFields();
            messageLabel.setText("Grade added!");
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            Grade selected = gradeTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Delete grade for " + selected.getEnrollmentId() + "?");

                if (confirm.showAndWait().get() == ButtonType.OK) {
                    gradeService.deleteGrade(selected.getEnrollmentId());
                    refreshTable();
                    messageLabel.setText("Grade deleted!");
                }
            } else {
                messageLabel.setText("Select a grade first");
            }
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/DashboardView.fxml"));
            Stage stage = (Stage) gradeTable.getScene().getWindow();

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
            gradeList.setAll(gradeService.getAllGrades());
            gradeTable.setItems(gradeList);

            if (gradeList.isEmpty()) {
                messageLabel.setText("No grades found");
            } else {
                messageLabel.setText("Showing " + gradeList.size() + " grades");
            }
        } catch (Exception e) {
            System.out.println("ERROR in refreshTable: " + e.getMessage());
            messageLabel.setText("Error loading data");
        }
    }

    private void clearFields() {
        enrollmentIdField.clear();
        marksField.clear();
        gradeField.clear();
    }

    private void setFieldsEditable(boolean editable) {
        enrollmentIdField.setEditable(editable);
        marksField.setEditable(editable);
        gradeField.setEditable(editable);
    }
}