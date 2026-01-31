package service.implementations;

import model.Student;
import repository.StudentRepository;
import service.interfaces.StudentService;
import java.time.LocalDate;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepo = new StudentRepository();

    @Override
    public void addStudent(String id, String name, LocalDate dateOfBirth, String email) {
        studentRepo.addStudent(new Student(id, name, dateOfBirth, email));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    @Override
    public void deleteStudent(String id) {
        studentRepo.deleteStudent(id);
    }
}