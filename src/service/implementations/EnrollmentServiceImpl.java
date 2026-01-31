package service.implementations;

import model.Enrollment;
import repository.EnrollmentRepository;
import service.interfaces.EnrollmentService;

import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {
    private EnrollmentRepository enrollmentRepo = new EnrollmentRepository();

    @Override
    public void addEnrollment(String id, String studentId, String courseCode, String semester) {
        enrollmentRepo.addEnrollment(new Enrollment(id, studentId, courseCode, semester));
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.getAllEnrollments();
    }

    @Override
    public void deleteEnrollment(String id) {
        enrollmentRepo.deleteEnrollment(id);
    }
}