package service.implementations;

import model.Course;
import repository.CourseRepository;
import service.interfaces.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepo = new CourseRepository();

    @Override
    public void addCourse(String code, String name, int credits) {
        courseRepo.addCourse(new Course(code, name, credits));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    @Override
    public void deleteCourse(String code) {
        courseRepo.deleteCourse(code);
    }
}