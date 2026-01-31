package service.interfaces;

import model.Enrollment;
import java.util.List;

public interface EnrollmentService {
    void addEnrollment(String id, String studentId, String courseCode, String semester);
    List<Enrollment> getAllEnrollments();
    void deleteEnrollment(String id);
}
