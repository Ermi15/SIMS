package service.interfaces;

import model.Student;
import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    void addStudent(String id, String name, LocalDate dateOfBirth, String email);
    List<Student> getAllStudents();
    void deleteStudent(String id);
}
