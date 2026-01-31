package service.implementations;

import model.Grade;
import repository.GradeRepository;
import service.interfaces.GradeService;

import java.util.List;

public class GradeServiceImpl implements GradeService {
    private GradeRepository gradeRepo = new GradeRepository();

    @Override
    public void addGrade(String enrollmentId, double marks, String grade) {
        gradeRepo.addGrade(new Grade(enrollmentId, marks, grade));
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepo.getAllGrades();
    }

    @Override
    public void deleteGrade(String enrollmentId) {
        gradeRepo.deleteGrade(enrollmentId);
    }
}