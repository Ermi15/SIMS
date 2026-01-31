package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;
import model.Student;
import service.interfaces.StudentService;
import service.implementations.StudentServiceImpl;
import util.Validation;

import java.time.LocalDate;

public class StudentController {
    @FXML private TextField idField, nameField, dobField, emailField;
    @FXML private Button addBtn, deleteBtn;
    @FXML private TableView<Student> studentTable;
    @FXML private Label titleLabel, messageLabel;
    @FXML private TableColumn<Student, String> ageColumn;

    private StudentService studentService;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private boolean isManageMode = false;

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
        refreshTable();
    }

    private StudentService getStudentService() {
        if (studentService == null) {
            studentService = new StudentServiceImpl();
        }
        return studentService;
    }

    public void setManageMode(boolean isManage) {
        this.isManageMode = isManage;
        titleLabel.setText(isManage ? "Manage Students" : "View Students");
        addBtn.setVisible(isManage);
        deleteBtn.setVisible(isManage);
        setFieldsEditable(isManage);
    }

    @FXML
    public void initialize() {
        ageColumn.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.valueOf(cellData.getValue().getAge())
                )
        );

        setManageMode(false);
    }

    @FXML
    private void handleAdd() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dob = dobField.getText().trim();
            String email = emailField.getText().trim();

            if (!Validation.notEmpty(id)) {
                messageLabel.setText("ID is required");
                return;
            }
            if (!Validation.notEmpty(name)) {
                messageLabel.setText("Name is required");
                return;
            }
            if (!Validation.notEmpty(dob)) {
                messageLabel.setText("Date of Birth is required");
                return;
            }
            if (!Validation.notEmpty(email) || !email.contains("@")) {
                messageLabel.setText("Valid email is required");
                return;
            }

            LocalDate dateOfBirth;
            try {
                dateOfBirth = LocalDate.parse(dob);
            } catch (Exception e) {
                messageLabel.setText("Invalid date format (use YYYY-MM-DD)");
                return;
            }

            getStudentService().addStudent(id, name, dateOfBirth, email);
            refreshTable();
            clearFields();
            messageLabel.setText("Student added!");
        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setContentText("Delete student: " + selected.getName() + "?");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                getStudentService().deleteStudent(selected.getId());
                refreshTable();
                messageLabel.setText("Student deleted!");
            }
        } else {
            messageLabel.setText("Select a student first");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/DashboardView.fxml"));
            Stage stage = (Stage) studentTable.getScene().getWindow();

            Scene scene = new Scene(root,800,600);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            messageLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        studentList.setAll(getStudentService().getAllStudents());
        studentTable.setItems(studentList);
        studentTable.refresh();
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        dobField.clear();
        emailField.clear();
    }

    private void setFieldsEditable(boolean editable) {
        idField.setEditable(editable);
        nameField.setEditable(editable);
        dobField.setEditable(editable);
        emailField.setEditable(editable);
    }
}