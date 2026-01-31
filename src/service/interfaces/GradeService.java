package service.interfaces;

import model.Grade;
import java.util.List;

public interface GradeService {
    void addGrade(String enrollmentId, double marks, String grade);
    List<Grade> getAllGrades();
    void deleteGrade(String enrollmentId);
}
